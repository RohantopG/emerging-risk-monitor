import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import ListPage from "./pages/ListPage";
import DetailPage from "./pages/DetailPage";
import Analytics from "./pages/Analytics";
import RiskForm from "./pages/RiskForm"; // ✅ ADD THIS

function App() {
  return (
    <BrowserRouter>
      {/* NAVBAR */}
      <div style={{
        background: "#1B4F8A",
        padding: "10px",
        display: "flex",
        gap: "15px",
        flexWrap: "wrap"
      }}>
        <Link style={{ color: "white" }} to="/login">Login</Link>
        <Link style={{ color: "white" }} to="/">Dashboard</Link>
        <Link style={{ color: "white" }} to="/list">List</Link>
        <Link style={{ color: "white" }} to="/detail">Detail</Link>
        <Link style={{ color: "white" }} to="/analytics">Analytics</Link>
        <Link style={{ color: "white" }} to="/create">Create</Link> {/* ✅ NEW */}
      </div>

      {/* PAGES */}
      <div style={{ padding: "20px" }}>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Dashboard />} />
          <Route path="/list" element={<ListPage />} />
         ✅ CORRECT
<Route path="/detail" element={<DetailPage />} />
          <Route path="/analytics" element={<Analytics />} />
          <Route path="/create" element={<RiskForm />} /> {/* ✅ NEW */}
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;