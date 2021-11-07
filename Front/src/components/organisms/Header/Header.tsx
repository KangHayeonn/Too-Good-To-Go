import React, { useState } from "react";
import { Link } from "react-router-dom";

import "./Header.css";
import styled from "@emotion/styled";
import SearchIcon from "@mui/icons-material/Search";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

const NavBar = styled.nav`
	background-color: #54b689;
	position: fixed;
	z-index: 100;
	left: 0;
	top: 0;
	width: 100%;
	height: 52px;
	border-bottom: 1px solid #d6d6d6;
`;

const Header: React.FC = () => {
	const [logged] = useState(false);
	return (
		<div>
			<NavBar>
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
								<Link to="/login">로그인</Link>
								<Link to="/register">회원가입</Link>
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
			</NavBar>
		</div>
	);
};

export default Header;
