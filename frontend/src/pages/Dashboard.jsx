import {
  BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid
} from "recharts";

function Dashboard() {
 const stored = JSON.parse(localStorage.getItem("risks")) || [];

const stats = {
  total: stored.length,
  high: stored.filter(r => r.riskLevel === "High").length,
  medium: stored.filter(r => r.riskLevel === "Medium").length,
  low: stored.filter(r => r.riskLevel === "Low").length
};

  const chartData = [
    { name: "High", value: stats.high },
    { name: "Medium", value: stats.medium },
    { name: "Low", value: stats.low }
  ];

  return (
    <div>
      <h2>Dashboard</h2>

      {/* CARDS */}
      <div style={{ display: "flex", gap: "15px", flexWrap: "wrap",justifyContent: "center" }}>
        {["total", "high", "medium", "low"].map((key) => (
          <div key={key} style={{
            flex: "1 1 200px",
            border: "1px solid #ddd",
            padding: "15px",
            borderRadius: "8px"
            
          }}>
            <h4>{key.toUpperCase()}</h4>
            <p>{stats[key]}</p>
          </div>
        ))}
      </div>

      {/* CHART */}
      <div style={{ marginTop: "20px", overflowX: "auto" }}>
        <BarChart width={400} height={300} data={chartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Bar dataKey="value" fill="#1B4F8A" />
        </BarChart>
      </div>
    </div>
  );
}

export default Dashboard;