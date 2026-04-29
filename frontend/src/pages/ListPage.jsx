import { useEffect, useState } from "react";

function ListPage() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1); // start from 1

  const pageSize = 2;

  useEffect(() => {
    setTimeout(() => {
      const allData = [
        { id: 1, name: "Risk A", riskLevel: "High" },
        { id: 2, name: "Risk B", riskLevel: "Low" },
        { id: 3, name: "Risk C", riskLevel: "Medium" },
        { id: 4, name: "Risk D", riskLevel: "High" }
      ];

      setData(allData);
      setLoading(false);
    }, 1000);
  }, []);

  // Pagination logic
  const paginatedData = data.slice(
    (page - 1) * pageSize,
    page * pageSize
  );

  const totalPages = Math.ceil(data.length / pageSize);

  // Sorting
  const handleSort = (field) => {
    const sorted = [...data].sort((a, b) =>
      a[field] > b[field] ? 1 : -1
    );
    setData(sorted);
  };

  if (loading) return <p>Loading...</p>;
  if (data.length === 0) return <p>No data available</p>;

  return (
    <div>
      <h2>Risk List</h2>

      <table border="1">
        <thead>
          <tr>
            <th onClick={() => handleSort("id")}>ID</th>
            <th onClick={() => handleSort("name")}>Name</th>
            <th onClick={() => handleSort("riskLevel")}>Risk Level</th>
          </tr>
        </thead>

        <tbody>
          {paginatedData.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.name}</td>
              <td>{item.riskLevel}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <br />

      {/* Pagination */}
      <button onClick={() => setPage(page - 1)} disabled={page === 1}>
        Prev
      </button>

      <span> Page {page} of {totalPages} </span>

      <button
        onClick={() => setPage(page + 1)}
        disabled={page === totalPages}
      >
        Next
      </button>
    </div>
  );
}

export default ListPage;