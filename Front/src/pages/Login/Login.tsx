import React, { useState, useEffect } from "react";
import styled from "@emotion/styled";
import { css } from "@emotion/react";
import { Link } from "react-router-dom";
import axios from "axios";

const LOGIN_URL = "https://2afbd7ad-e608-4067-ba51-6367a5d5cf96.mock.pstmn.io";

const Login: React.FC = () => {
	const [inputId, setInputId] = useState("");
	const [inputPw, setInputPw] = useState("");

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
		useEffect(() => {
			axios
				.post(`${LOGIN_URL}/login`, {
					user_id: inputId,
					user_pw: inputPw,
				})
				.then((res) => {
					console.log(res);
					console.log("res.data.userId :: ", res.data.userId);
					console.log("res.data.msg :: ", res.data.userPw);
					if (res.data.userId === undefined) {
						// id가 일치하지 않는 경우
						console.log("-------------------", res.data.msg);
						alert("입력하신 id가 일치하지 않습니다.");
					} else if (res.data.userId === null) {
						// id는 있지만, pw가 다른 경우 (userId = null, msg : undefined)
						console.log(
							"-------------------",
							"입력하신 비밀번호가 일치하지 않습니다."
						);
						alert("입력하신 id가 일치하지 않습니다.");
					} else if (res.data.userId === inputId) {
						// id, pw 모두 일치 (userId = userId1, msg = undefined)
						console.log("-------------------", "로그인 성공");
						sessionStorage.setItem("user_id", inputId);
					}
					document.location.href = "/";
				})
				.catch();
		});
	};

	useEffect(() => {
		axios
			.get(`${LOGIN_URL}/login`)
			.then((res) => console.log(res))
			.catch();
	});

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
					<button type="button" className="btn register-btn">
						<Link to="/register" css={register}>
							회원가입
						</Link>
					</button>
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
	}

	button:hover {
		background-color: #fff;
		color: #54b689;
		border: 2px solid #54b689;
	}
`;

const register = css`
	color: #fff;
	&:hover {
		color: #54b689;
	}
`;

export default Login;
