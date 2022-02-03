import React from "react";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import "./Header.css";
import { css } from "@emotion/react";
import SearchIcon from "@mui/icons-material/Search";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { RootState } from "../../../app/store";
import { logout } from "../../../modules/user";
import { initializeForm } from "../../../modules/auth";

const navBar = css`
	background-color: #54b689;
	border-bottom: 1px solid #d6d6d6;
`;

const Header: React.FC = () => {
	const { user } = useSelector(( state : RootState ) => ({ user : state.user.user }));
	const dispatch = useDispatch();
	const onLogout = () => {
		dispatch(logout());
		dispatch(initializeForm());
	};

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
									<Link to="/profile">
										<AccountCircleIcon id="accountIcon" />
									</Link>
									<Link to="/cart">
										<ShoppingCartIcon id="cartIcon" />
									</Link>
								</div>
								<button type="button" onClick={onLogout}>로그아웃</button>
							</div>
						)}
					</div>
					<Link to="/cart">장바구니</Link>
					<Link to="/profile">profile</Link>
				</div>
			</nav>
		</div>
	);
};

export default Header;
