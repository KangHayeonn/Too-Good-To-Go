import React from "react";
import styled from "@emotion/styled";

const Login: React.FC = () => {
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
						name=""
						id=""
						placeholder="아이디를 입력하세요."
					/>
					<input
						className="password input"
						type="password"
						name=""
						id=""
						placeholder="비밀번호를 입력하세요."
					/>
				</InputCtn>
				<BtnCtn>
					<button type="button" className="btn login-btn">
						로그인
					</button>
					<button type="button" className="btn register-btn">
						회원가입
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
	height: 467px;
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

export default Login;
