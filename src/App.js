import "./App.css";
import Header from "./components/common/Header";
import Dashboard from "./components/dashboard/Dashboard";
import HomePage from "./components/homepage/HomePage";
import {BrowserRouter as Router, Switch, Route} from "react-router-dom";
import LRDetails from "./components/dashboard/LRDetails";
import Footer from "./components/common/Footer";

function App() {
  return (
    <>
      <div className="app-container">
        <Header />
        <Router>
          <Switch>
            <Route exact path="/" component={HomePage}></Route>
            <Route path="/dashboard" component={Dashboard}></Route>
            <Route path="/lr-details" component={LRDetails}></Route>
          </Switch>
        </Router>
        <Footer />
      </div>
    </>
  );
}

export default App;
