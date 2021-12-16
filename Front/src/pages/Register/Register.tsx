import React from "react";
import styled from "@emotion/styled";

const Register: React.FC = () => {
	return (
		<Wrapper>
			<Container>
				<TitleCtn>
					<h4>회원가입</h4>
					<img src="image/Line 13.png" alt="" />
					<h4>SIGN UP</h4>
				</TitleCtn>
				<InputCtn>
					<div className="input-row"> 라디오 버튼 이후 추가 </div>
					<div className="input-row">
						{" "}
						<input
							className="id input"
							type="text"
							name=""
							id=""
							placeholder="아이디를 입력하세요."
						/>{" "}
						<Confirm type="button" className="confirm-btn">
							중복확인
						</Confirm>
					</div>
					<div className="input-row">
						<input
							className="password input"
							type="password"
							name=""
							id=""
							placeholder="비밀번호를 입력하세요."
						/>
						<Confirm type="button" className="confirm-btn">
							확인
						</Confirm>
					</div>
					<div className="input-row">
						<input
							className="password-confirm input"
							type="password"
							name=""
							id=""
							placeholder="비밀번호를 확인하세요."
						/>
						<Confirm type="button" className="confirm-btn">
							확인
						</Confirm>
					</div>
				</InputCtn>
				<BtnCtn>
					<Button type="button" className="btn register-confirm-btn">
						회원가입 완료
					</Button>
				</BtnCtn>
			</Container>
		</Wrapper>
	);
};

const Button = styled.button`
	background-color: #54b689;
`;

const Confirm = styled.button`
	width: 100px;
	height: 49px;
	background-color: #e7e4e4;
	margin: 0;
	padding: 0;
	margin-left: -2px;
	font-size: 16px;
	&:hover {
		background-color: #54b689;
		color: #fff;
	}
`;

const Wrapper = styled.div`
	margin-top: 20px;
	border: 1px solid #c4c4c4;
	border-radius: 2px;
	padding: 10px;
	background-color: white;
	width: 480px;
	height: 620px;
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
	margin: 50px;
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;

	input {
		font-size: 16px;
		font-weight: bold;
		width: 200px;
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

	button.btn {
		width: 241px;
		height: 53px;
		border-radius: 12px;
	}
	button.register-confirm-btn {
		margin-top: 10px;
		background-color: #54b689;
	}
	button {
		font-size: 16px;
		font-weight: bold;
		color: #ffffff;
		border: none;
		margin: 8px;
	}
	button:hover {
		background-color: #fff;
		color: #54b689;
		border: 2px solid #54b689;
	}
`;

export default Register;
