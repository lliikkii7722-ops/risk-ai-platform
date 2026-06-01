import "./Sidebar.css";

function Sidebar({ activePage, setActivePage }) {
  const menuItems = [
    "Dashboard",
    "Projects",
    "Team",
    "Tasks",
    "Risks",
    "Quality",
    "Milestones",
    "AI Assistant",
    "Reports",
  ];

  return (
    <aside className="sidebar">
      <div className="brand">
        <h2>Risk AI</h2>
        <p>Project Intelligence</p>
      </div>

      <nav>
        {menuItems.map((item) => (
          <button
            key={item}
            className={activePage === item ? "nav-item active" : "nav-item"}
            onClick={() => setActivePage(item)}
          >
            {item}
          </button>
        ))}
      </nav>
    </aside>
  );
}

export default Sidebar;