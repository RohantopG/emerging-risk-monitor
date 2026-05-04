import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = () => {
    if (username === "admin" && password === "1234") {
      setMessage("Login Successful ✅");

      setTimeout(() => {
        localStorage.setItem("token", "dummy-jwt");
        navigate("/");
      }, 1000);
    } else {
      setMessage("Invalid credentials ❌");
    }
  };

  return (
    <div
      style={{
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f5f7fa"
      }}
    >
      <div
        style={{
          width: "350px",
          padding: "30px",
          borderRadius: "10px",
          backgroundColor: "white",
          boxShadow: "0 4px 12px rgba(0,0,0,0.1)"
        }}
      >
        <h2 style={{ textAlign: "center" }}>Login</h2>

        <input
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          style={{
            width: "90%",
            padding: "10px",
            marginTop: "15px",
            borderRadius: "5px",
            border: "1px solid #ccc"
          }}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={{
            width: "90%",
            padding: "10px",
            marginTop: "15px",
            borderRadius: "5px",
            border: "1px solid #ccc"
          }}
        />

        <button
          onClick={handleLogin}
          style={{
            width: "97%",
            padding: "10px",
            marginTop: "15px",
            backgroundColor: "#1B4F8A",
            color: "white",
            border: "none",
            borderRadius: "5px"
          }}
        >
          Login
        </button>

        {/* ✅ MESSAGE HERE */}
        <p style={{ textAlign: "center", marginTop: "10px" }}>
          {message}
        </p>
      </div>
    </div>
  );
}

export default Login;