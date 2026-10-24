import { useEffect, useState, type ReactNode } from 'react';
import { api } from './api';
import ResourcePage from './ResourcePage';
import './App.css';

function Balance() {
  const [balance, setBalance] = useState<number | null>(null);
  const refresh = () => api<{ balance: number }>('/cashflow/balance').then((d) => setBalance(d.balance));
  useEffect(() => {
    refresh();
  }, []);
  return (
    <p className="balance">
      Current balance: <b>{balance ?? '...'}</b> <button onClick={refresh}>Refresh</button>
    </p>
  );
}

const pages: { name: string; element: ReactNode }[] = [
  {
    name: 'Users',
    element: (
      <ResourcePage
        title="Users" path="/users"
        fields={[{ key: 'name', label: 'Name' }, { key: 'email', label: 'Email' }, { key: 'role', label: 'Role (ADMIN/STAFF/CUSTOMER)' }]}
        columns={['id', 'name', 'email', 'role']}
      />
    ),
  },
  {
    name: 'Suppliers',
    element: (
      <ResourcePage
        title="Suppliers" path="/suppliers"
        fields={[{ key: 'name', label: 'Name' }, { key: 'phone', label: 'Phone' }, { key: 'email', label: 'Email' }]}
        columns={['id', 'name', 'phone', 'email']}
      />
    ),
  },
  {
    name: 'Inventory',
    element: (
      <ResourcePage
        title="Inventory" path="/inventory"
        fields={[
          { key: 'name', label: 'Item name' },
          { key: 'quantity', label: 'Quantity', numeric: true },
          { key: 'unitPrice', label: 'Unit price', numeric: true },
          { key: 'supplierId', label: 'Supplier ID', numeric: true },
        ]}
        columns={['id', 'name', 'quantity', 'unitPrice', 'supplierId']}
        actions={[
          {
            label: 'Restock',
            run: async (row) => {
              const q = Number(prompt('Quantity to add?'));
              const c = Number(prompt('Cost per unit?'));
              if (q > 0 && c >= 0) await api(`/inventory/${row.id}/restock`, 'POST', { quantity: q, unitCost: c });
            },
          },
        ]}
      />
    ),
  },
  {
    name: 'Orders',
    element: (
      <ResourcePage
        title="Orders" path="/orders" canDelete={false}
        fields={[
          { key: 'userId', label: 'User ID', numeric: true },
          { key: 'itemId', label: 'Item ID', numeric: true },
          { key: 'quantity', label: 'Quantity', numeric: true },
        ]}
        columns={['id', 'userId', 'itemId', 'quantity', 'totalAmount', 'status']}
      />
    ),
  },
  {
    name: 'Bills',
    element: (
      <ResourcePage
        title="Bills" path="/bills" canDelete={false} fields={[]}
        columns={['id', 'orderId', 'amount', 'paid', 'issuedAt']}
        actions={[{ label: 'Pay', run: async (row) => { await api(`/bills/${row.id}/pay`, 'POST'); } }]}
      />
    ),
  },
  {
    name: 'Cash Flow',
    element: (
      <ResourcePage
        title="Cash Flow" path="/cashflow"
        fields={[{ key: 'type', label: 'IN or OUT' }, { key: 'amount', label: 'Amount', numeric: true }, { key: 'description', label: 'Description' }]}
        columns={['id', 'type', 'amount', 'description', 'createdAt']}
      />
    ),
  },
];

export default function App() {
  const [tab, setTab] = useState(0);
  return (
    <div className="app">
      <h1>Account Management System</h1>
      <nav>
        {pages.map((p, i) => (
          <button key={p.name} className={i === tab ? 'active' : ''} onClick={() => setTab(i)}>{p.name}</button>
        ))}
      </nav>
      <div key={tab}>
        {pages[tab].name === 'Cash Flow' && <Balance />}
        {pages[tab].element}
      </div>
    </div>
  );
}
