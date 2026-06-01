import { useState } from "react";
import api from "../services/api";

export default function Login({ onLogin }) {
  const [mode, setMode] = useState("login");

  const [name, setName] = useState("Arjun Kumar");
  const [email, setEmail] = useState("arjun@example.com");
  const [password, setPassword] = useState("123456");
  const [role, setRole] = useState("MANAGER");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    try {
      setLoading(true);

      const url = mode === "login" ? "/api/auth/login" : "/api/auth/register";

      const payload =
        mode === "login"
          ? { email, password }
          : { name, email, password, role };

      const response = await api.post(url, payload);

      localStorage.setItem("token", response.data.token);
      onLogin();
    } catch (error) {
      alert(mode === "login" ? "Login failed" : "Registration failed");
      console.log(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <h1>Risk AI Platform</h1>
        <p>Intelligent Software Project Risk Management System</p>

        {mode === "register" && (
          <>
            <input
              type="text"
              placeholder="Full name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />

            <select value={role} onChange={(e) => setRole(e.target.value)}>
              <option value="ADMIN">Admin</option>
              <option value="MANAGER">Manager</option>
              <option value="TEAM_MEMBER">Team Member</option>
            </select>
          </>
        )}

        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={handleSubmit}>
          {loading ? "Please wait..." : mode === "login" ? "Login" : "Create Account"}
        </button>

        <button
          className="link-btn"
          onClick={() => setMode(mode === "login" ? "register" : "login")}
        >
          {mode === "login"
            ? "New user? Create an account"
            : "Already have an account? Login"}
        </button>
      </div>
    </div>
  );
}