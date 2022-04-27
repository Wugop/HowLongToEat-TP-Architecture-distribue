import React, { useState } from "react";
import Log from "./component/Log";
import Page from "./component/Page";
import Search from "./component/Search";
import "./style/index.scss";

export const UserContext = React.createContext();

function App() {
  const [logger, setLogger] = useState(false);
  const [token, setToken] = useState("");
  const [username, setUsername] = useState("");
  const [page, setPage] = useState("");

  const accueil = () => {
    setLogger(false);
    setPage("");
  };

  return (
    <div className="app">
      <UserContext.Provider value={{ username, token, logger }}>
        <div className="title">
          <div className="title-first" onClick={accueil}>HowLongTo<div className="title-second">Eat</div></div>
          {!token && !logger && <input type="button" onClick={() => setLogger(true)} value="Se Connecter" id="join" />}
          {
            token &&
            <div className="disconnectButton">
              <div className="username">{username}</div>
              <input type="button" onClick={() => {
                setToken("");
                setUsername("");
              }} value="Se Déconnecter" id="join" />
            </div>
          }
        </div>
        <div className="content">

          {logger && <Log setLogger={setLogger} setToken={setToken} setUsername={setUsername} />}
          {!logger && !page && <Search setPage={setPage}/>}
          {page && !logger && <Page dataResto={page}/>}

        </div>
      </UserContext.Provider>
    </div>
  );
}

export default App;
