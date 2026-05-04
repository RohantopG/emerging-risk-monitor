import { useNavigate, useLocation } from "react-router-dom";
import { useState, useEffect } from "react";

function RiskForm() {
  const navigate = useNavigate();
  const location = useLocation();

  const editData = location.state;

  const [name, setName] = useState("");
  const [level, setLevel] = useState("Low");

  useEffect(() => {
    if (editData) {
      setName(editData.name);
      setLevel(editData.riskLevel);
    }
  }, [editData]);

  const handleSubmit = () => {
    const stored = JSON.parse(localStorage.getItem("risks")) || [];

    if (editData) {
      // EDIT
      const updated = stored.map((item) =>
        item.id === editData.id
          ? { ...item, name, riskLevel: level }
          : item
      );
      localStorage.setItem("risks", JSON.stringify(updated));
    } else {
      // CREATE
      const newRisk = {
        id: Date.now(),
        name,
        riskLevel: level
      };
      localStorage.setItem("risks", JSON.stringify([...stored, newRisk]));
    }

    navigate("/list");
  };

  return (
  <div style={{
    minHeight: "80vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    background: "#f5f7fb"
  }}>
    <div style={{
      width: "100%",
      maxWidth: "420px",
      background: "white",
      padding: "30px",
      borderRadius: "12px",
      boxShadow: "0 4px 12px rgba(0,0,0,0.1)"
    }}>
      
      <h2 style={{
        textAlign: "center",
        marginBottom: "25px",
        color: "#1B4F8A"
      }}>
        {editData ? "Edit Risk" : "Create Risk"}
      </h2>

      {/* INPUT */}
      <div style={{ marginBottom: "15px" }}>
        <label style={{ fontSize: "14px", fontWeight: "bold" }}>
          Risk Name
        </label>
        <input
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Enter risk name"
          style={{
            width: "95%",
            padding: "10px",
            marginTop: "5px",
            borderRadius: "6px",
            border: "1px solid #ccc",
            outline: "none"
          }}
        />
      </div>

      {/* SELECT */}
      <div style={{ marginBottom: "20px" }}>
        <label style={{ fontSize: "14px", fontWeight: "bold" }}>
          Risk Level
        </label>
        <select
          value={level}
          onChange={(e) => setLevel(e.target.value)}
          placeholder="All"
          style={{
            width: "100%",
            padding: "10px",
            marginTop: "5px",
            borderRadius: "6px",
            border: "1px solid #ccc"
          }}
        >
          <option value="">All</option>
          <option value="High">High</option>
          <option value="Medium">Medium</option>
          <option value="Low">Low</option>
        </select>
      </div>

      {/* BUTTON */}
      <button
        onClick={handleSubmit}
        style={{
          width: "100%",
          padding: "12px",
          background: "#1B4F8A",
          color: "white",
          border: "none",
          borderRadius: "8px",
          fontWeight: "bold",
          cursor: "pointer",
          transition: "0.3s"
        }}
        onMouseOver={(e) => e.target.style.background = "#163d6b"}
        onMouseOut={(e) => e.target.style.background = "#1B4F8A"}
      >
        {editData ? "Save Changes" : "Submit Risk"}
      </button>

    </div>
  </div>
);}

export default RiskForm;