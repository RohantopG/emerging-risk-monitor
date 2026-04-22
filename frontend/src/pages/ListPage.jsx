import { useEffect, useState } from "react";

function ListPage() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Dummy data (temporary)
    setTimeout(() => {
      setData([
        { id: 1, name: "Risk A", riskLevel: "High" },
        { id: 2, name: "Risk B", riskLevel: "Low" }
      ]);
      setLoading(false);
    }, 1000);
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  if (data.length === 0) {
    return <p>No data available</p>;
  }

  return (
    <table border="1">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Risk Level</th>
        </tr>
      </thead>
      <tbody>
        {data.map((item) => (
          <tr key={item.id}>
            <td>{item.id}</td>
            <td>{item.name}</td>
            <td>{item.riskLevel}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default ListPage;