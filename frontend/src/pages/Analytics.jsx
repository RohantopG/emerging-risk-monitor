import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  LineChart,
  Line,
  PieChart,
  Pie,
  Cell,
  Legend
} from "recharts";

function Analytics() {
 const stored = JSON.parse(localStorage.getItem("risks")) || [];

const barData = [
  { name: "High", value: stored.filter(r => r.riskLevel === "High").length },
  { name: "Medium", value: stored.filter(r => r.riskLevel === "Medium").length },
  { name: "Low", value: stored.filter(r => r.riskLevel === "Low").length }
];


  const lineData = [
    { month: "Jan", value: 2 },
    { month: "Feb", value: 3 },
    { month: "Mar", value: 5 },
    { month: "Apr", value: 4 },
    { month: "May", value: 6 },
    { month: "Jun", value: 7 }
  ];

  const pieData = barData; // reuse same data

  // 🎨 Colors
  const COLORS = ["#ff4d4f", "#faad14", "#52c41a"];

  return (
    <div className="container">
      <h2>Analytics</h2>

      {/* BAR */}
      <div className="card">
        <h3>Bar Chart</h3>
        <BarChart width={400} height={300} data={barData}>
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Bar dataKey="value" fill="#1b4f8a68" />
        </BarChart>
      </div>

      {/* LINE */}
      <div className="card">
        <h3>Line Chart</h3>
        <LineChart width={400} height={300} data={lineData}>
          <XAxis dataKey="month" />
          <YAxis />
          <Tooltip />
          <Line dataKey="value" stroke="#1B4F8A" />
        </LineChart>
      </div>

      {/* PIE FIXED ✅ */}
      <div className="card">
        <h3>Pie Chart</h3>
        <PieChart width={400} height={300}>
          <Pie
            data={pieData}
            dataKey="value"
            nameKey="name"
            cx="50%"
            cy="50%"
            outerRadius={100}
            label   // 👈 important
          >
            {pieData.map((entry, index) => (
              <Cell key={index} fill={COLORS[index]} />
            ))}
          </Pie>

          <Tooltip />
          <Legend />
        </PieChart>
      </div>
    </div>
  );
}

export default Analytics;