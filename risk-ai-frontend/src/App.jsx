import { useEffect, useState } from "react";
import "./App.css";
import Login from "./pages/Login";
import api from "./services/api";

function App() {
  const [page, setPage] = useState("Dashboard");
  const [loggedIn, setLoggedIn] = useState(!!localStorage.getItem("token"));
  const [showProjectModal, setShowProjectModal] = useState(false);

  if (!loggedIn) {
    return <Login onLogin={() => setLoggedIn(true)} />;
  }

  const logout = () => {
    localStorage.removeItem("token");
    setLoggedIn(false);
  };
  const openProjectModal = () => {
    setShowProjectModal(true);
  };

  const menu = [
    "Dashboard",
    "Projects",
    "Team",
    "Tasks",
    "Risk Registry",
    "AI Mitigation",
    "Quality Risks",
    "Budget Tracker",
    "Timeline",
    "Resources",
    "Reports",
  ];

  return (
    <div className="layout">
      <aside className="sidebar">
        <div className="logo">RA</div>
        <h2>RiskAI Platform</h2>
        <p>AI Risk Intelligence</p>

        <nav>
          {menu.map((item) => (
            <button
              key={item}
              onClick={() => setPage(item)}
              className={page === item ? "active" : ""}
            >
              {item}
            </button>
          ))}
        </nav>

        <div className="profile">
          <div className="avatar">AK</div>
          <div>
            <b>Arjun Kumar</b>
            <span>Project Manager</span>
          </div>
        </div>
      </aside>

      <main className="content">
        <div className="logout-row">
          <button className="logout-btn" onClick={logout}>Logout</button>
        </div>

       {page === "Dashboard" && <Dashboard openProjectModal={openProjectModal} />}
       {page === "Projects" && <Projects openProjectModal={openProjectModal} />}
       {page === "Team" && <Team openProjectModal={openProjectModal} />}
       {page === "Tasks" && <Tasks openProjectModal={openProjectModal} />}
       {page === "Risk Registry" && <RiskRegistry openProjectModal={openProjectModal} />}
       {page === "AI Mitigation" && <AIMitigation openProjectModal={openProjectModal} />}
       {page === "Quality Risks" && <QualityRisks openProjectModal={openProjectModal} />}
       {page === "Budget Tracker" && <BudgetTracker openProjectModal={openProjectModal} />}
       {page === "Timeline" && <Timeline openProjectModal={openProjectModal} />}
       {page === "Resources" && <Resources openProjectModal={openProjectModal} />}
       {page === "Reports" && <Reports openProjectModal={openProjectModal} />}
     </main>


     {showProjectModal && (
       <ProjectModal
         onClose={() => setShowProjectModal(false)}
       />
     )}

     </div>
  );
}

function Header({ title, subtitle, openProjectModal }) {
  const downloadExcel = async () => {
    const response = await api.get("/api/reports/projects/excel", {
      responseType: "blob",
    });

    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", "projects-report.xlsx");
    document.body.appendChild(link);
    link.click();
    link.remove();
  };

  return (
    <div className="header">
      <div>
        <h1>{title}</h1>
        <p>{subtitle}</p>
      </div>
      <div className="actions">
   <button
     type="button"
     className="new-project-btn"
     onClick={openProjectModal}
   >
     + New Project
   </button>

        <button onClick={downloadExcel}>Export Report</button>
      </div>
    </div>
  );
}

function Card({ title, value, note, danger, warning }) {
  return (
    <div className="card">
      <p>{title}</p>
      <h2 className={danger ? "danger" : warning ? "warning" : ""}>{value}</h2>
      <span>{note}</span>
    </div>
  );
}

function Dashboard({ openProjectModal }) {
  const [summary, setSummary] = useState({
    totalProjects: 0,
    averageBudgetUsedPercentage: 0,
    averageResourceUtilizationPercentage: 0,
    highDefectProjects: 0
  });

  useEffect(() => {
    const token = localStorage.getItem("token");

    console.log("TOKEN:", token);

    api
      .get("/api/dashboard/summary")
      .then((res) => {
        console.log("Dashboard SUCCESS:", res.data);

        setSummary({
          totalProjects: res.data.totalProjects || 0,
          averageBudgetUsedPercentage:
            res.data.averageBudgetUsedPercentage || 0,
          averageResourceUtilizationPercentage:
            res.data.averageResourceUtilizationPercentage || 0,
          highDefectProjects:
            res.data.highDefectProjects || 0,
        });
      })
      .catch((err) => {
        console.error("Dashboard ERROR:", err);
        console.log("STATUS:", err.response?.status);
        console.log("DATA:", err.response?.data);
      });
  }, []);
  return (
    <>
     <Header
       title="Project Risk Dashboard"
       subtitle="Live project intelligence overview"
       openProjectModal={openProjectModal}
     />
      <div className="stats">
        <Card title="Total Projects" value={summary?.totalProjects ?? 0} note="Active projects" />
        <Card title="Budget Used" value={`${summary?.averageBudgetUsedPercentage ?? 0}%`} note="Average utilization" />
        <Card title="Resource Usage" value={`${summary?.averageResourceUtilizationPercentage ?? 0}%`} note="Average workload" warning />
        <Card title="High Defect Projects" value={summary?.highDefectProjects ?? 0} note="Needs attention" danger />
      </div>

      <section className="ai-box">
        <h3>AI Mitigation Suggestions</h3>
        <p>Critical project risks are analyzed using local Ollama AI.</p>
        <p>Resource utilization and quality issues are tracked for early warning.</p>
        <p>Use the AI Mitigation page to ask risk-related questions.</p>
      </section>

      <div className="grid-2">
        <section className="panel">
          <h3>Risk by Category</h3>
          {["Technical debt", "Resource burnout", "Scope creep", "Integration failures", "Security vulnerabilities"].map((r, i) => (
            <div className="risk-row" key={r}>
              <span>{r}</span>
              <div className="bar"><div style={{ width: `${85 - i * 12}%` }}></div></div>
              <b>{84 - i * 13}</b>
            </div>
          ))}
        </section>

        <section className="panel">
          <h3>Budget Breakdown</h3>
          <div className="donut">68%</div>
          <p>Development ₹4.5L</p>
          <p>Infrastructure ₹2.1L</p>
          <p>QA & Testing ₹1.6L</p>
        </section>
      </div>
    </>
  );
}

function Projects({ openProjectModal }) {
   const [projects, setProjects] = useState([]);

   useEffect(() => {
     api.get("/api/projects")
       .then((res) => setProjects(res.data))
       .catch((err) => console.log(err));
   }, []);

   return (
     <>
       <Header
         title="All Projects"
         subtitle={`${projects.length} active projects across teams`}
         openProjectModal={openProjectModal}
       />
       <div className="project-filters">
         <button>All</button>
         <button>Active</button>
         <button>At Risk</button>
         <button>Completed</button>
       </div>

       <section className="panel table-wrap">
         <table className="project-table">
           <thead>
             <tr>
               <th>Project</th>
               <th>Status</th>
               <th>Risk Score</th>
               <th>Budget</th>
               <th>Deadline</th>
               <th>Manager</th>
             </tr>
           </thead>

           <tbody>
             {projects.map((p) => {
               const riskScore = Math.min(
                 100,
                 Math.round((p.defects / Math.max(p.totalTasks, 1)) * 100)
               );

               return (
                 <tr key={p.id}>
                   <td><strong>{p.projectName}</strong></td>
                   <td><span className="badge risk">At Risk</span></td>
                   <td className="danger">{riskScore}</td>
                   <td>₹{p.actualCost} / ₹{p.plannedBudget}</td>
                   <td>{p.plannedDays} days</td>
                   <td>Arjun K.</td>
                 </tr>
               );
             })}
           </tbody>
         </table>
       </section>
     </>
   );
 }

function Team({ openProjectModal }) {
  const [members, setMembers] = useState([]);

  useEffect(() => {
    api.get("/api/team-members")
      .then((res) => setMembers(res.data))
      .catch((err) => console.log(err));
  }, []);

  return (
    <>
     <Header
       title="Resource Utilization"
       subtitle={`${members.length} team members tracked`}
       openProjectModal={openProjectModal}
     />
      <div className="stats">
        <Card title="Team Members" value={members.length} note="Active members" />
        <Card
          title="Avg. Utilization"
          value={
            members.length
              ? `${Math.round(
                  members.reduce(
                    (sum, m) => sum + (m.allocatedHours / m.availableHours) * 100,
                    0
                  ) / members.length
                )}%`
              : "0%"
          }
          note="Average workload"
          warning
        />
        <Card
          title="Overloaded"
          value={
            members.filter(
              (m) => (m.allocatedHours / m.availableHours) * 100 > 85
            ).length
          }
          note="Above 85%"
          danger
        />
      </div>

      <section className="panel">
        <h3>Team utilization</h3>

        {members.map((m) => {
          const utilization = Math.round(
            (m.allocatedHours / m.availableHours) * 100
          );

          return (
            <div className="team-row" key={m.id}>
              <div className="member-info">
                <div className="avatar small">
                  {m.name?.split(" ").map((x) => x[0]).join("").slice(0, 2)}
                </div>

                <div>
                  <strong>{m.name}</strong>
                  <p>{m.role}</p>
                </div>
              </div>

              <div className="bar">
                <div style={{ width: `${utilization}%` }}></div>
              </div>

              <b className={utilization > 85 ? "danger" : "warning"}>
                {utilization}%
              </b>
            </div>

          );
        })}
      </section>
    </>
  );
}

function Tasks({ openProjectModal }) {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    api.get("/api/tasks")
      .then((res) => setTasks(res.data))
      .catch((err) => console.log(err));
  }, []);

  return (
   <>
     <Header
       title="Task Management"
       subtitle={`${tasks.length} tasks tracked`}
       openProjectModal={openProjectModal}
     />

     <div className="filters">
       <button className="active">All</button>
       <button>In Progress</button>
       <button>Completed</button>
     </div>

     <Table
       headers={[
         "Task",
         "Status",
         "Deadline",
         "Project",
         "Assigned To"
       ]}
       rows={tasks.map((t) => [
         t.title,
         t.status,
         t.deadline,
         t.project?.projectName,
         t.assignedTo?.name
       ])}
     />
   </>
  );
}

function RiskRegistry({ openProjectModal }) {
  const [risks, setRisks] = useState([]);

  useEffect(() => {
    api.get("/api/risks")
      .then((res) => setRisks(res.data))
      .catch((err) => console.log(err));
  }, []);

  return (
    <>
      <Header title="Risk Registry" subtitle={`${risks.length} risks tracked`} openProjectModal={openProjectModal} />
      <Table
        headers={["Risk", "Category", "Severity", "Likelihood", "Score", "Owner", "Status"]}
        rows={risks.map((r) => [
          r.title,
          r.category,
          r.severity,
          r.likelihood,
          r.score,
          r.owner,
          r.status,
        ])}
      />
    </>
  );
}
function AIMitigation({ openProjectModal }) {
  const [question, setQuestion] = useState("How can I reduce project budget risk?");
  const [answer, setAnswer] = useState("");

  const askAI = async () => {
    setAnswer("Generating AI response...");
    try {
      const res = await api.post("/api/ai/chat", { question });
      setAnswer(res.data.answer);
    } catch {
      setAnswer("AI service failed. Make sure Ollama is running.");
    }
  };

  return (
    <>
      <Header
        title="AI Mitigation Engine"
        subtitle="Powered by Ollama local LLM"
        openProjectModal={openProjectModal}
      />

      <div className="ai-search">
        <textarea
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="Ask Ollama e.g. How do I reduce technical debt?"
        />

        <div className="ai-action">
           <button
              className="primary-btn"
              onClick={askAI}
           >
               Ask AI
           </button>
        </div>
      </div>

      <div className="ai-grid">

        <div className="card">
          <h3>AI Risk Predictions</h3>

          <div className="prediction">
            Database migration risk may delay Sprint 5
            <span>93% confidence</span>
          </div>

          <div className="prediction">
            Resource burnout likely in backend team
            <span>78% confidence</span>
          </div>

          <div className="prediction">
            Security risk trending down after fixes
            <span>82% confidence</span>
          </div>
        </div>

        <div className="card">
          <h3>Suggested Mitigations</h3>

          <ul>
            <li>Enable parallel DB migration</li>
            <li>Redistribute tasks from backend team</li>
            <li>Review project scope</li>
            <li>Enable cloud auto-scaling</li>
          </ul>
        </div>

      </div>

      {answer && (
        <div className="card">
          <h3>Ollama Response</h3>
          <p>{answer}</p>
        </div>
      )}
    </>
  );
}

function QualityRisks({ openProjectModal }) {
  const [qualityRisks, setQualityRisks] = useState([]);

  useEffect(() => {
    api.get("/api/quality-risks")
      .then((res) => setQualityRisks(res.data))
      .catch((err) => console.log(err));
  }, []);

  const first = qualityRisks[0];

  return (
    <>
      <Header
        title="Quality Risk Tracker"
        subtitle="Code health, bugs, and tech debt"
        openProjectModal={openProjectModal}
      />

      <div className="stats">
        <Card
          title="Code Coverage"
          value={`${first?.codeCoverage ?? 0}%`}
          note="Target: 80%"
          warning
        />

        <Card
          title="Open Bugs"
          value={first?.openBugs ?? 0}
          note="Critical bugs"
          danger
        />

        <Card
          title="Tech Debt Hours"
          value={first?.techDebtHours ?? 0}
          note="Needs cleanup"
          danger
        />

        <Card
          title="Defect Escape Rate"
          value={`${first?.defectEscapeRate ?? 0}%`}
          note="Industry: 2%"
        />
      </div>

      <Table
        headers={[
          "Quality Risk",
          "Module",
          "Severity",
          "Impact",
          "Action",
          "Coverage",
          "Bugs",
        ]}
        rows={qualityRisks.map((q) => [
          q.riskTitle,
          q.moduleName,
          q.severity,
          q.impact,
          q.action,
          `${q.codeCoverage}%`,
          q.openBugs,
        ])}
      />
    </>
  );
}
function BudgetTracker({ openProjectModal }) {
  return (
    <>
      <Header
        title="Budget Tracker"
        subtitle="Project cost and spending health"
        openProjectModal={openProjectModal}
      />

      <div className="stats">
        <Card
          title="Total Budget"
          value="₹12.0L"
          note="Approved"
        />

        <Card
          title="Spent To Date"
          value="₹8.2L"
          note="68.3% used"
        />

        <Card
          title="Remaining"
          value="₹3.8L"
          note="31.6% buffer"
        />
      </div>

      <section className="panel">
        <h3>Budget by Category</h3>

        <p>
          Development — ₹4.5L / ₹6.0L
        </p>

        <div className="progress">
          <div
            className="progress-fill"
            style={{ width: "75%" }}
          ></div>
        </div>

        <p>
          Infrastructure — ₹2.1L / ₹3.0L
        </p>

        <div className="progress">
          <div
            className="progress-fill"
            style={{ width: "70%" }}
          ></div>
        </div>

        <p>
          QA & Testing — ₹1.6L / ₹3.0L
        </p>

        <div className="progress">
          <div
            className="progress-fill"
            style={{ width: "53%" }}
          ></div>
        </div>
      </section>

      <section className="panel">
        <h3>Budget Health Summary</h3>

        <div className="risk-list">

          <div className="risk-item">
            <span>Budget Utilization</span>
            <strong>68.3%</strong>
          </div>

          <div className="risk-item">
            <span>Cost Variance</span>
            <strong style={{ color: "#22c55e" }}>
              +₹0.8L Saved
            </strong>
          </div>

          <div className="risk-item">
            <span>Forecast Completion Cost</span>
            <strong>₹11.2L</strong>
          </div>

          <div className="risk-item">
            <span>Budget Risk Level</span>
            <strong style={{ color: "#f59e0b" }}>
              MEDIUM
            </strong>
          </div>

        </div>
      </section>
    </>
  );
}

function Timeline({ openProjectModal }) {
  return (
    <>
      <Header
        title="Timeline & Milestones"
        subtitle="Sprint tracking and delivery status"
        openProjectModal={openProjectModal}
      />

      <section className="panel">

        <div className="milestone-card">
          <div className="milestone-header">
            <h3>Sprint 1 — Foundation & Setup</h3>
            <span className="status-done">DONE</span>
          </div>

          <p>Apr 01 - Apr 14</p>

          <div className="progress">
            <div className="progress-fill" style={{ width: "100%" }}></div>
          </div>
        </div>

        <div className="milestone-card">
          <div className="milestone-header">
            <h3>Sprint 2 — Core APIs</h3>
            <span className="status-done">DONE</span>
          </div>

          <p>Apr 15 - Apr 28</p>

          <div className="progress">
            <div className="progress-fill" style={{ width: "100%" }}></div>
          </div>
        </div>

        <div className="milestone-card">
          <div className="milestone-header">
            <h3>Sprint 3 — Risk Engine + Budget</h3>
            <span className="status-done">DONE</span>
          </div>

          <p>Apr 29 - May 12</p>

          <div className="progress">
            <div className="progress-fill" style={{ width: "100%" }}></div>
          </div>
        </div>

        <div className="milestone-card">
          <div className="milestone-header">
            <h3>Sprint 4 — AI Mitigation + Ollama</h3>
            <span className="status-active">ACTIVE</span>
          </div>

          <p>May 13 - May 26</p>

          <div className="progress">
            <div
              className="progress-fill"
              style={{ width: "78%" }}
            ></div>
          </div>
        </div>

        <div className="milestone-card">
          <div className="milestone-header">
            <h3>Sprint 5 — Reports + Docker</h3>
            <span className="status-risk">AT RISK</span>
          </div>

          <p>May 27 - Jun 09</p>

          <div className="progress">
            <div
              className="progress-fill risk-fill"
              style={{ width: "25%" }}
            ></div>
          </div>
        </div>

      </section>
    </>
  );
}

function Resources({ openProjectModal }) {
   const [members, setMembers] = useState([]);

   useEffect(() => {
     api.get("/api/team-members")
       .then((res) => setMembers(res.data))
       .catch((err) => console.log(err));
   }, []);

   const avgUtilization =
     members.length > 0
       ? (
           members.reduce(
             (sum, m) => sum + (m.allocatedHours / m.availableHours) * 100,
             0
           ) / members.length
         ).toFixed(1)
       : 0;

   const overloaded = members.filter(
     (m) => (m.allocatedHours / m.availableHours) * 100 > 85
   ).length;

   return (
     <>
       <Header
         title="Resource Utilization"
         subtitle={`${members.length} active members`}
         openProjectModal={openProjectModal}
       />

       <div className="stats">
         <Card
           title="Team Members"
           value={members.length}
           note="Active members"
         />

         <Card
           title="Avg Utilization"
           value={`${avgUtilization}%`}
           note="Average workload"
         />

         <Card
           title="Overloaded"
           value={overloaded}
           note="Above 85%"
         />
       </div>

       <section className="panel">
         <h3>Team Utilization</h3>

         {members.map((m) => {
           const utilization = (
             (m.allocatedHours / m.availableHours) *
             100
           ).toFixed(1);

           return (
             <div className="resource-card" key={m.id}>

               <div className="resource-left">
                 <div className="avatar">
                   {m.name?.charAt(0)}
                 </div>

                 <div>
                   <h4>{m.name}</h4>
                   <p>{m.role || "Team Member"}</p>
                 </div>
               </div>

               <div className="resource-right">
                 <div className="progress">
                   <div
                     className="progress-fill"
                     style={{
                       width: `${utilization}%`
                     }}
                   ></div>
                 </div>

                 <b>{utilization}%</b>
               </div>

             </div>
           );
         })}
       </section>
     </>
   );
 }

function Reports({ openProjectModal }) {
   const downloadExcel = async () => {
     const response = await api.get("/api/reports/projects/excel", {
       responseType: "blob",
     });

     const url = window.URL.createObjectURL(new Blob([response.data]));
     const link = document.createElement("a");
     link.href = url;
     link.setAttribute("download", "projects-report.xlsx");
     document.body.appendChild(link);
     link.click();
     link.remove();
   };

   return (
     <>
       <Header
         title="Reports & Exports"
         subtitle="Generate Excel reports and API documents"
         openProjectModal={openProjectModal}
       />

       <div className="stats">
         <div className="card">
           <p>Project Report</p>
           <h2>Excel</h2>
           <span>Export project data</span>
           <button className="primary-btn" onClick={downloadExcel}>
             Download Excel
           </button>
         </div>

         <div className="card">
           <p>Risk Summary</p>
           <h2>PDF</h2>
           <span>Coming soon</span>
         </div>

         <div className="card">
           <p>Swagger Docs</p>
           <h2>Live</h2>
           <span>API documentation</span>
         </div>
       </div>

       <section className="panel">
         <h3>Swagger API Docs</h3>
         <p>
           All backend APIs are documented and testable using Swagger UI.
         </p>

         <div className="risk-list">
           <div className="risk-item">
             <span>POST /api/auth/login</span>
             <strong>Auth</strong>
           </div>

           <div className="risk-item">
             <span>GET /api/projects</span>
             <strong>Projects</strong>
           </div>

           <div className="risk-item">
             <span>GET /api/dashboard/summary</span>
             <strong>Analytics</strong>
           </div>

           <div className="risk-item">
             <span>POST /api/ai/chat</span>
             <strong>AI</strong>
           </div>

           <div className="risk-item">
             <span>GET /api/reports/projects/excel</span>
             <strong>Report</strong>
           </div>
         </div>
       </section>
     </>
   );
 }

function Table({ headers, rows }) {
  return (
    <section className="panel table-wrap">
      <table>
        <thead>
          <tr>{headers.map((h) => <th key={h}>{h}</th>)}</tr>
        </thead>
        <tbody>
          {rows.map((row, i) => (
            <tr key={i}>{row.map((c, j) => <td key={j}>{c}</td>)}</tr>
          ))}
        </tbody>
      </table>
    </section>
  );
}
function ProjectModal({ onClose }) {
  const [project, setProject] = useState({
    projectName: "",
    complexity: "MEDIUM",
    plannedBudget: 100000,
    actualCost: 0,
    plannedDays: 90,
    completedDays: 0,
    totalTasks: 50,
    completedTasks: 0,
    defects: 0,
    testingCoverage: 0,
    availableHours: 500,
    workedHours: 0,
  });

  const handleChange = (e) => {
    setProject({
      ...project,
      [e.target.name]: e.target.value,
    });
  };

  const saveProject = async () => {
    try {
      await api.post("/api/projects", {
        ...project,
        plannedBudget: Number(project.plannedBudget),
        actualCost: Number(project.actualCost),
        plannedDays: Number(project.plannedDays),
        completedDays: Number(project.completedDays),
        totalTasks: Number(project.totalTasks),
        completedTasks: Number(project.completedTasks),
        defects: Number(project.defects),
        testingCoverage: Number(project.testingCoverage),
        availableHours: Number(project.availableHours),
        workedHours: Number(project.workedHours),
      });

      alert("Project created successfully");
      onClose();
      window.location.reload();
    } catch (err) {
      console.log(err);
      alert("Project creation failed");
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>Create Project</h2>

        <input name="projectName" placeholder="Project Name" onChange={handleChange} />

        <select name="complexity" value={project.complexity} onChange={handleChange}>
          <option>LOW</option>
          <option>MEDIUM</option>
          <option>HIGH</option>
        </select>

        <input name="plannedBudget" type="number" placeholder="Planned Budget" onChange={handleChange} />
        <input name="actualCost" type="number" placeholder="Actual Cost" onChange={handleChange} />
        <input name="plannedDays" type="number" placeholder="Planned Days" onChange={handleChange} />
        <input name="completedDays" type="number" placeholder="Completed Days" onChange={handleChange} />
        <input name="totalTasks" type="number" placeholder="Total Tasks" onChange={handleChange} />
        <input name="completedTasks" type="number" placeholder="Completed Tasks" onChange={handleChange} />
        <input name="defects" type="number" placeholder="Defects" onChange={handleChange} />
        <input name="testingCoverage" type="number" placeholder="Testing Coverage %" onChange={handleChange} />
        <input name="availableHours" type="number" placeholder="Available Hours" onChange={handleChange} />
        <input name="workedHours" type="number" placeholder="Worked Hours" onChange={handleChange} />

        <div className="modal-actions">
          <button onClick={saveProject} className="primary-btn">Save</button>
          <button onClick={onClose}>Cancel</button>
        </div>
      </div>
    </div>
  );
}
export default App;