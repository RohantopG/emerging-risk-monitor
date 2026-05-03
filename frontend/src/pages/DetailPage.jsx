import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

function DetailPage() {
  const location = useLocation();
  const navigate = useNavigate();

  const risk = location.state;

  const [aiLoading, setAiLoading] = useState(false);
  const [aiResponse, setAiResponse] = useState("");

  if (!risk) {
    return <h3>No Risk Selected ❌ (Go from List Page)</h3>;
  }

  const getColor = (level) => {
    if (level === "High") return "red";
    if (level === "Medium") return "orange";
    return "green";
  };

  // ✅ DELETE
  const handleDelete = () => {
    const stored = JSON.parse(localStorage.getItem("risks")) || [];

    const updated = stored.filter((item) => item.id !== risk.id);

    localStorage.setItem("risks", JSON.stringify(updated));

    navigate("/list");
  };

  // ✅ EDIT
  const handleEdit = () => {
    navigate("/create", { state: risk });
  };

  const handleAskAI = () => {
  setAiLoading(true);

  setTimeout(() => {
    let response = "";

    if (risk.riskLevel === "High") {
      response = "🔴 High Risk: Immediate action required!";
    } else if (risk.riskLevel === "Medium") {
      response = "🟠 Medium Risk: Monitor closely.";
    } else {
      response = "🟢 Low Risk: No immediate action needed.";
    }

    setAiResponse(response);
    setAiLoading(false);
  }, 1500);
};

 return (
  <div style={{
    padding: "30px",
    background: "#f5f7fa",
    minHeight: "100vh"
  }}>
    <div style={{
      maxWidth: "600px",
      margin: "auto",
      background: "white",
      padding: "25px",
      borderRadius: "12px",
      boxShadow: "0 4px 12px rgba(0,0,0,0.1)"
    }}>
      <h2 style={{ color: "#1B4F8A", marginBottom: "20px" }}>
        Risk Detail
      </h2>

      <p><b>ID:</b> {risk.id}</p>
      <p><b>Name:</b> {risk.name}</p>
      <p><b>Description:</b> {risk.description || "N/A"}</p>

      <p>
        <b>Risk Level:</b>{" "}
        <span style={{
          padding: "5px 10px",
          borderRadius: "6px",
          color: "white",
          background:
            risk.riskLevel === "High"
              ? "red"
              : risk.riskLevel === "Medium"
              ? "orange"
              : "green"
        }}>
          {risk.riskLevel}
        </span>
      </p>

      <p><b>Score:</b> {risk.score || "N/A"}</p>

      {/* ACTIONS */}
      <div style={{ marginTop: "20px" }}>
        <button
          onClick={handleEdit}
          style={{
            padding: "8px 12px",
            background: "#1B4F8A",
            color: "white",
            border: "none",
            borderRadius: "6px"
          }}
        >
          Edit
        </button>

        <button
          onClick={handleDelete}
          style={{
            padding: "8px 12px",
            background: "red",
            color: "white",
            border: "none",
            borderRadius: "6px",
            marginLeft: "10px"
          }}
        >
          Delete
        </button>
      </div>

      <hr style={{ margin: "20px 0" }} />

      <h3>AI Analysis</h3>

      <button
        onClick={handleAskAI}
        style={{
          padding: "8px 12px",
          background: "#1B4F8A",
          color: "white",
          border: "none",
          borderRadius: "6px"
        }}
      >
        Ask AI
      </button>

      {aiLoading && <p>Loading...</p>}
      {aiResponse && <p>{aiResponse}</p>}
    </div>
  </div>
);
}

export default DetailPage;