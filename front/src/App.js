import "./App.css"
import React from "react";
import AccueilProfil from "./AccueilProfil/AccueilProfil";


function App() {
  return (<AccueilProfil></AccueilProfil>);
  /*return(
      <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-10 h-100">
              <Spinner animation="border" variant="primary" />
              <Spinner animation="border" variant="secondary" />
              <Spinner animation="border" variant="success" />
              <Spinner animation="border" variant="danger" />
              <Spinner animation="border" variant="warning" />
              <Spinner animation="border" variant="info" />
              <Spinner animation="border" variant="light" />
              <Spinner animation="border" variant="dark" />
            </div>
      </div>
  );*/
}

export default App;
