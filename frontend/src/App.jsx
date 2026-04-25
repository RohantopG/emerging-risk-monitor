import { useContext } from "react";
import Login from "./pages/Login";
import ListPage from "./pages/ListPage";
import { AuthProvider, AuthContext } from "./context/AuthContext";
import ProtectedRoute from "./components/ProtectedRoute";

function AppContent() {
  const { isAuthenticated, login } = useContext(AuthContext);

  return isAuthenticated ? (
    <ProtectedRoute>
      <ListPage />
    </ProtectedRoute>
  ) : (
    <Login onLogin={login} />
  );
}

function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
}

export default App;