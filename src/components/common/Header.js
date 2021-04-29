import React from "react";

import {saveUser, getUser} from "../util/User";
import logo from "./../../images/logo.jpg";
import logout from "./../../images/logout.png";

const Header = () => {
  const user = getUser();

  const logoutHandler = () => {
    saveUser("", "", "", "", false);
    window.location.pathname = "/";
  };
  return (
    <div className="header">
      <span style={{flex: 5}}>
        <img className="header-img" src={logo} />
      </span>
      {user && user.isLogin === "true" ? (
        <span style={{flex: 1, display: "flex", direction: "row"}}>
          <div style={{flex: 2}}>
            {user.name} <br /> {user.role}
          </div>
          <div style={{flex: 1.5}}>
            <img className="logout-img" src={logout} onClick={logoutHandler} />
          </div>
        </span>
      ) : null}
    </div>
  );
};
export default Header;
