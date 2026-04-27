import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid
} from "recharts";

function Dashboard() {
  // Dummy stats (simulate API /stats)
  const stats = {
    total: 10,
    high: 4,
    medium: 3,
    low: 3
  };

  // Chart data
  const chartData = [
    { name: "High", value: stats.high },
    { name: "Medium", value: stats.medium },
    { name: "Low", value: stats.low }
  ];

  return (
    <div style={{ padding: "20px" }}>
      <h2>Dashboard</h2>

      {/* KPI Cards */}
      <div style={{ display: "flex", gap: "20px" }}>
        <div style={{ border: "1px solid", padding: "10px" }}>
          <h4>Total Risks</h4>
          <p>{stats.total}</p>
        </div>

        <div style={{ border: "1px solid", padding: "10px" }}>
          <h4>High</h4>
          <p>{stats.high}</p>
        </div>

        <div style={{ border: "1px solid", padding: "10px" }}>
          <h4>Medium</h4>
          <p>{stats.medium}</p>
        </div>

        <div style={{ border: "1px solid", padding: "10px" }}>
          <h4>Low</h4>
          <p>{stats.low}</p>
        </div>
      </div>

      <br />

      {/* Bar Chart */}
      <BarChart width={400} height={300} data={chartData}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis />
        <Tooltip />
        <Bar dataKey="value" />
      </BarChart>
    </div>
  );
}

export default Dashboard;