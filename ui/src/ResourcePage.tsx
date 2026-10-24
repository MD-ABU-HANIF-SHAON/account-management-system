import { useEffect, useState, type FormEvent } from 'react';
import { api } from './api';

export type Row = Record<string, string | number | boolean | null>;
export type Field = { key: string; label: string; numeric?: boolean };
export type RowAction = { label: string; run: (row: Row) => Promise<void> };

type Props = {
  title: string;
  path: string;
  fields: Field[];
  columns: string[];
  actions?: RowAction[];
  canDelete?: boolean;
};

export default function ResourcePage({ title, path, fields, columns, actions = [], canDelete = true }: Props) {
  const [rows, setRows] = useState<Row[]>([]);
  const [form, setForm] = useState<Record<string, string>>({});
  const [error, setError] = useState('');

  async function load() {
    try {
      setRows(await api<Row[]>(path));
      setError('');
    } catch (e) {
      setError((e as Error).message);
    }
  }

  useEffect(() => {
    load();
  }, [path]);

  async function attempt(work: () => Promise<unknown>) {
    try {
      await work();
      await load();
    } catch (e) {
      setError((e as Error).message);
    }
  }

  function submit(e: FormEvent) {
    e.preventDefault();
    const body: Record<string, string | number> = {};
    fields.forEach((f) => {
      body[f.key] = f.numeric ? Number(form[f.key]) : (form[f.key] ?? '');
    });
    attempt(async () => {
      await api(path, 'POST', body);
      setForm({});
    });
  }

  return (
    <section>
      <h2>{title}</h2>
      {error && <p className="error">{error}</p>}

      {fields.length > 0 && (
        <form onSubmit={submit}>
          {fields.map((f) => (
            <input
              key={f.key}
              type={f.numeric ? 'number' : 'text'}
              step="any"
              placeholder={f.label}
              value={form[f.key] ?? ''}
              onChange={(e) => setForm({ ...form, [f.key]: e.target.value })}
              required
            />
          ))}
          <button type="submit">Add</button>
        </form>
      )}

      <table>
        <thead>
          <tr>
            {columns.map((c) => <th key={c}>{c}</th>)}
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {rows.map((r) => (
            <tr key={String(r.id)}>
              {columns.map((c) => <td key={c}>{String(r[c] ?? '')}</td>)}
              <td>
                {actions.map((a) => (
                  <button key={a.label} onClick={() => attempt(() => a.run(r))}>{a.label}</button>
                ))}
                {canDelete && (
                  <button onClick={() => attempt(() => api(path + '/' + r.id, 'DELETE'))}>Delete</button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
}
