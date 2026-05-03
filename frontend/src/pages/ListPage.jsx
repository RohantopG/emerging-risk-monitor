import { useNavigate, useSearchParams } from "react-router-dom";
import { useEffect, useState } from "react";

function ListPage() {
  const [data, setData] = useState([]);
  const [search, setSearch] = useState("");
  const [debouncedSearch, setDebouncedSearch] = useState("");
  const [status, setStatus] = useState("");
  const [page, setPage] = useState(1);
  const [streamData, setStreamData] = useState("");

  const navigate = useNavigate();
  const [params, setParams] = useSearchParams();

  const pageSize = 2;

  // 🔹 Load data from localStorage
  useEffect(() => {
    const stored = JSON.parse(localStorage.getItem("risks")) || [];

    if (stored.length === 0) {
      const defaultData = [
        { id: 1, name: "Risk A", riskLevel: "High" },
        { id: 2, name: "Risk B", riskLevel: "Low" }
      ];
      setData(defaultData);
      localStorage.setItem("risks", JSON.stringify(defaultData));
    } else {
      setData(stored);
    }
  }, []);

  // 🔹 URL params
  useEffect(() => {
    setSearch(params.get("search") || "");
    setStatus(params.get("status") || "");
    setPage(Number(params.get("page")) || 1);
  }, []);

  // 🔹 Debounce
  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedSearch(search);
    }, 300);
    return () => clearTimeout(timer);
  }, [search]);

  // 🔹 Update URL
  useEffect(() => {
    setParams({ search, status, page });
  }, [search, status, page]);

  // 🔹 Filter
  const filteredData = data.filter((item) =>
    item.name.toLowerCase().includes(debouncedSearch.toLowerCase()) &&
    (status === "" || item.riskLevel === status)
  );

  const paginatedData = filteredData.slice(
    (page - 1) * pageSize,
    page * pageSize
  );

  const totalPages = Math.ceil(filteredData.length / pageSize);

  // ✅ DELETE
  const handleDelete = (id) => {
    const updated = data.filter((item) => item.id !== id);
    setData(updated);
    localStorage.setItem("risks", JSON.stringify(updated));
  };

  // ✅ EDIT
  const handleEdit = (item) => {
    navigate("/create", { state: item });
  };

  const exportCSV = () => {
    alert("CSV Downloaded (dummy)");
  };

  const startStream = () => {
    setStreamData("Generating...");
    setTimeout(() => {
      setStreamData("Report Ready ✅");
    }, 2000);
  };

  return (
  <div style={{
    padding: "30px",
    background: "#f5f7fa",
    minHeight: "100vh"
  }}>
    <h2 style={{
      color: "#1B4F8A",
      marginBottom: "20px"
    }}>
      Risk List
    </h2>

    {/* CREATE BUTTON */}
    <button
      onClick={() => navigate("/create", { state: null })}
      style={{
        marginBottom: "20px",
        padding: "10px 15px",
        background: "#1B4F8A",
        color: "white",
        border: "none",
        borderRadius: "6px",
        cursor: "pointer"
      }}
    >
      ➕ Create Risk
    </button>

    {/* FILTER CARD */}
    <div style={{
      background: "white",
      padding: "20px",
      borderRadius: "10px",
      boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
      marginBottom: "25px"
    }}>
      <div style={{ display: "flex", gap: "10px", flexWrap: "wrap" }}>
        <input
          placeholder="Search..."
          value={search}
          onChange={(e) => {
            setSearch(e.target.value);
            setPage(1);
          }}
          style={{
            padding: "10px",
            borderRadius: "6px",
            border: "1px solid #ccc"
          }}
        />

        <select
          value={status}
          onChange={(e) => {
            setStatus(e.target.value);
            setPage(1);
          }}
          style={{
            padding: "10px",
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

      <div style={{ marginTop: "15px" }}>
        <button onClick={exportCSV} style={{
          padding: "8px 12px",
          marginRight: "10px",
          borderRadius: "6px",
          border: "none",
          background: "#1B4F8A",
          color: "white"
        }}>
          Download CSV
        </button>

        <button onClick={startStream} style={{
          padding: "8px 12px",
          borderRadius: "6px",
          border: "none",
          background: "#1B4F8A",
          color: "white"
        }}>
          Generate Report
        </button>
      </div>

      <p style={{ marginTop: "10px" }}>{streamData}</p>
    </div>

    {/* TABLE */}
    <div style={{
      background: "white",
      padding: "20px",
      borderRadius: "10px",
      boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
    }}>
     <table width="100%" style={{ borderCollapse: "collapse", border: "1px solid #ccc" }}>
  <thead>
    <tr style={{ background: "#1B4F8A", color: "white" }}>
      <th style={{ padding: "12px", border: "1px solid #ccc" }}>ID</th>
<th style={{ border: "1px solid #ccc" }}>Name</th>
<th style={{ border: "1px solid #ccc" }}>Risk Level</th>
    </tr>
  </thead>

  <tbody>
    {paginatedData.map((item, index) => (
      <tr
        key={item.id}
        style={{
          textAlign: "center",
          background: index % 2 === 0 ? "#f9f9f9" : "white"
        }}
      >
       <td style={{ padding: "12px", border: "1px solid #ccc" }}>{item.id}</td>

        <td
          style={{
            border: "1px solid #ccc",
            cursor: "pointer",
            color: "#1B4F8A",
            fontWeight: "600"
          }}
          onClick={() => navigate("/detail", { state: item })}
        >
          {item.name}
        </td>

        <td style={{
           border: "1px solid #ccc", 
          fontWeight: "bold",
          color:
            item.riskLevel === "High"
              ? "red"
              : item.riskLevel === "Medium"
              ? "orange"
              : "green"
        }}>
          {item.riskLevel}
        </td>
      </tr>
    ))}
  </tbody>
</table>

{/* Pagination */}
      <div style={{
  marginTop: "20px",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  gap: "10px"
}}>
  <button
    onClick={() => setPage(page - 1)}
    disabled={page === 1}
    style={{
      padding: "8px 12px",
      borderRadius: "6px",
      border: "1px solid #ccc",
      cursor: "pointer"
    }}
  >
    ◀ Prev
  </button>

  <span style={{
    padding: "8px 12px",
    background: "#1B4F8A",
    color: "white",
    borderRadius: "6px"
  }}>
    {page} / {totalPages}
  </span>

  <button
    onClick={() => setPage(page + 1)}
    disabled={page === totalPages}
    style={{
      padding: "8px 12px",
      borderRadius: "6px",
      border: "1px solid #ccc",
      cursor: "pointer"
    }}
  >
    Next ▶
  </button>
</div>
    </div>
  </div>
);}

export default ListPage;