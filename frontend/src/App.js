import React, { useState } from "react";
import axios from "axios";
import "./App.css";
import myLogo from './logo.jpeg';
function App() {
  const [chat, setChat] = useState("");
  const [chatReply, setChatReply] = useState("");
  const [advice, setAdvice] = useState("");
  const [category, setCategory] = useState("students");
  const [started, setStarted] = useState(false);

  // 🔥 CLEAN AI TEXT
  const formatText = (text) => {
    if (!text) return "";

    return text
      .replace(/[#*]/g, "") // remove * and #
      .split("\n")
      .map((line, i) => <p key={i}>{line}</p>);
  };

  const handleChat = async () => {
    const res = await axios.post("http://localhost:5000/chat", {
      message: chat,
      category: category,
    });

    setChatReply(res.data.reply);
    setAdvice("");
  };

  const handleAdvice = async () => {
    const res = await axios.post("http://localhost:5000/suggest", {
      text: chatReply,
      category: category,
    });

    setAdvice(res.data.advice);
  };

  return (
    <div className="App">

      {/* 🔥 NAVBAR */}
      <nav className="navbar">
      <div className="image"><img src={myLogo} alt="Company Logo" /></div>
        <div>
          <button onClick={() => setStarted(true)}>Get Started</button>
          <button onClick={() => setStarted(true)}>Login</button>
          <button onClick={() => setStarted(true)}>Register</button>
        </div>
      </nav>

      {/* 🔥 HERO SECTION */}
      {!started && (
        <div className="hero">
          <div className="hero-left">
            <h1>We Simplify Government Schemes</h1>
            <p>AI-powered insights for students, professionals & more</p>
            <button onClick={() => setStarted(true)}>Get Started →</button>
          </div>

          <div className="hero-right">
            <div className="shape"><img src={myLogo} alt="Company Logo" /></div>
          </div>
        </div>
      )}

      {/* 🔥 DASHBOARD */}
      {started && (
        <div className="dashboard">

          {/* CATEGORY */}
          <div className="category-box">
            <label>Select Category</label>
            <select value={category} onChange={(e) => setCategory(e.target.value)}>
              <option value="students">Students</option>
              <option value="jobs">Working</option>
              <option value="pension">Pension</option>
              <option value="women">Women</option>
            </select>
          </div>

          {/* CHAT */}
          <div className="chat-box">
            <input
              value={chat}
              onChange={(e) => setChat(e.target.value)}
              placeholder="Ask about schemes..."
            />
            <button onClick={handleChat}>Ask</button>
          </div>

          {/* RESPONSE */}
          {chatReply && (
            <div className="response-box">
              <h3>AI Response</h3>
              {formatText(chatReply)}

              <button onClick={handleAdvice} className="advice-btn">
                💡 Get Personalized Advice
              </button>

              {advice && (
                <div className="advice-box">
                  <h3>Advice</h3>
                  {formatText(advice)}
                </div>
              )}
            </div>
          )}
        </div>
      )}
    </div>
  );
}

export default App;