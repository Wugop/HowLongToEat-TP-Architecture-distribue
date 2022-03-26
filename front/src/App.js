import { useState } from "react";
import Log from "./component/Log";
import Search from "./component/Search";
import "./style/index.scss";

function App() {
  const [logger, setLogger] = useState(false);

  return (
    <div className="app">
      <div className="title">
        <div className="title-first">HowLongTo<div className="title-second">Eat</div></div>
        <input type="button" onClick={() => setLogger(true)} value="Se Connecter" id="join" />
      </div>
      <div className="content">
        {logger && <Log />}
        {!logger && <Search />}
      </div>
    </div>
  );
}

export default App;
