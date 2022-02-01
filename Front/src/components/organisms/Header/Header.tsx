import React, { useEffect } from "react";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import axios from "axios";
import "./Header.css";
import { css } from "@emotion/react";
import SearchIcon from "@mui/icons-material/Search";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { RootState } from "../../../app/store";
import { logout } from "../../../modules/user";

const LOGIN_URL = "http://54.180.134.20/api"; // http 붙여야함 (404 오류 방지)

const navBar = css`
	background-color: #54b689;
	border-bottom: 1px solid #d6d6d6;
`;

const Header: React.FC = () => {
	const { user } = useSelector(( state : RootState ) => ({ user : state.user.user }));
	const dispatch = useDispatch();
	const onLogout = () => {
		dispatch(logout());
	};

	useEffect(() => {
		axios
			.get(`${LOGIN_URL}/logout`)
			.then((res) => console.log(res))
			.catch();
	},[dispatch]);

	return (
		<div>
			<nav css={navBar}>
				<div className="wrap">
					<div className="home">
						<Link to="/">
							<img
								alt="logo"
								src={`${process.env.PUBLIC_URL}/image/Logo.png`} // public폴더 절대경로 사용시 환경변수에 접근해서 써야함.
							/>
						</Link>
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
						{!user ? (
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
								<a href="/logout" onClick={onLogout}>로그아웃</a>
							</div>
						)}
					</div>
				</div>
			</nav>
		</div>
	);
};

export default Header;
