import React, { useState } from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import "./Header.css";
import { css } from "@emotion/react";
import SearchIcon from "@mui/icons-material/Search";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import Login from "../../../pages/Login/Login";
import Register from "../../../pages/Register/Register";

const navBar = css`
	background-color: #54b689;
`;

const Header: React.FC = () => {
	const [logged] = useState(false);
	return (
		<div>
			<nav css={navBar}>
				<div className="wrap">
					<div className="home">
						<a href="/">
							<img alt="logo" src="image/Logo.png" />
						</a>
					</div>
					<div className="searchBar">
						<form>
							<input
								className="searchText"
								type="text"
								placeholder="원하시는 메뉴를 입력해주세요!"
							/>
							<a className="searchBtn" href="/">
								<SearchIcon id="searchIcon" />
							</a>
						</form>
					</div>
					<div className="account">
						{logged === false ? (
							<div className="loginState">
								<Router>
									<Link to="/login">Login</Link>
									<Link to="/register">Register</Link>
									<Switch>
										<Route exact path="/login">
											<Login />
										</Route>
									</Switch>
									<Switch>
										<Route exact path="/register">
											<Register />
										</Route>
									</Switch>
								</Router>
							</div>
						) : (
							<div className="logoutState">
								<div className="navIcon">
									<a href="/my">
										<AccountCircleIcon id="accountIcon" />
									</a>
									<a href="/cart">
										<ShoppingCartIcon id="cartIcon" />
									</a>
								</div>
								<a href="/logout">로그아웃</a>
							</div>
						)}
					</div>
				</div>
			</nav>
		</div>
	);
};

export default Header;
