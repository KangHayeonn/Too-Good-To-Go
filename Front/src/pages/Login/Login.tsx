import React, { useState } from "react";
import styled from "@emotion/styled";
import { Link, useHistory } from "react-router-dom";
import axios from "axios";

const LOGIN_URL = "http://54.180.134.20/api"; // http 붙여야함 (404 오류 방지)
const JWT_EXPIREY_TIME = 24 * 3600 * 1000; // 만료시간 (24시간 밀리 초로 표현)

const Login: React.FC = () => {
	const [inputId, setInputId] = useState("");
	const [inputPw, setInputPw] = useState("");
	const history = useHistory();

	const handleInputId = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { value } = e.target;
		setInputId(value);
	};

	const handleInputPw = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { value } = e.target;
		setInputPw(value);
	};

	const onClickLogin = () => {
		console.log("click login");
		console.log("ID : ", inputId);
		console.log("Pw : ", inputPw);

		// hook call 에러 뜸 (handler 안에 useEffect 사용할 시)
		axios
			.post(`${LOGIN_URL}/login`, {
				email: inputId,
				password: inputPw,
			})
			.then((res) => {
				console.log(res);
				const { accessToken } = res.data.data;
				axios.defaults.headers.common.Authorization = accessToken
					? `${accessToken}`
					: "";
				console.log("로그인 성공");
				// accessToken 만료하기 1분 전에 로그인 연장
				setTimeout(onSlientRefresh, JWT_EXPIREY_TIME - 60000);
				history.push("/");
			})
			.catch((e) => {
				console.log("로그인 실패");
				console.error(e);
			});
	};

	const onSlientRefresh = () => {
		axios
			.post("/slient-refresh", {
				email: inputId,
				password: inputPw,
			})
			.then((res) => {
				console.log(res);
				console.log("로그인 성공");
			})
			.catch((e) => {
				console.log("실패");
				console.error(e);
			});
	};

	return (
		<Wrapper>
			<Container>
				<TitleCtn>
					<h4>로그인</h4>
					<img src="image/Line 13.png" alt="" />
					<h4>SIGN IN</h4>
				</TitleCtn>
				<InputCtn>
					<input
						className="id input"
						type="text"
						name="input_id"
						id="login_id"
						value={inputId}
						onChange={handleInputId}
						placeholder="아이디를 입력하세요."
					/>
					<input
						className="password input"
						type="password"
						name="input_pw"
						id="login_pw"
						value={inputPw}
						onChange={handleInputPw}
						placeholder="비밀번호를 입력하세요."
					/>
				</InputCtn>
				<BtnCtn>
					<button
						type="button"
						className="btn login-btn"
						onClick={onClickLogin}
					>
						로그인
					</button>
					<Link to="/register">
						<button type="button" className="btn register-btn">
							회원가입
						</button>
					</Link>
				</BtnCtn>
			</Container>
		</Wrapper>
	);
};

const Wrapper = styled.div`
	margin-top: 20px;
	border: 1px solid #c4c4c4;
	border-radius: 2px;
	padding: 10px;
	background-color: white;
	width: 400px;
	height: 490px;
`;

const Container = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
`;

const TitleCtn = styled.div`
	font-weight: bold;
	color: #646464;
	margin-top: 40px;
	width: 300px;
	display: flex;
	justify-content: space-evenly;
	font-size: 20px;

	img {
		width: 100px;
		height: 3px;
		margin-top: 14px;
	}
`;

const InputCtn = styled.div`
	height: 230px;
	width: 298px;
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;

	input {
		font-size: 16px;
		font-weight: bold;
		width: 280px;
		height: 45px;
		margin: 10px;
		padding-left: 20px;
		border: 1px solid #c9cbca;
	}
	input-row {
		width: 500px;
		margin: 0;
		padding: 0;
		display: inline-block;
	}
`;

const BtnCtn = styled.div`
	margin-top: -10px;
	button {
		font-size: 16px;
		font-weight: bold;
		color: #ffffff;
		border: none;
		margin: 8px;
	}
	button.btn {
		width: 241px;
		height: 53px;
		border-radius: 12px;
	}
	button.login-btn {
		background-color: #3171543d;
	}

	button.register-btn {
		margin-top: 10px;
		background-color: #54b689;
		color: #fff;
	}

	button:hover {
		background-color: #fff;
		color: #54b689;
		border: 2px solid #54b689;
	}
`;

export default Login;
