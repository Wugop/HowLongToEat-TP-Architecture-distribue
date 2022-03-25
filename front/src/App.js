import { useState } from "react";
import Log from "./component/Log";
import Search from "./component/Search";

function App() {
  const [loggedin, setLoggedin] = useState(null);

  return (
    <div className="App">
      <div className="title">
        <div className="title-first">How Long to </div>
        <div className="title-second">Eat</div>
      </div>
      {!loggedin && <Log />}
      {loggedin && <Search />}
    </div>
  );
}

export default App;
