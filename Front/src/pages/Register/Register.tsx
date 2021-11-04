import React from "react";
import "../Login/Login.css";
import styled from "@emotion/styled";

const Register: React.FC = () => {
	return (
		<Wrapper className="wrapper">
			<div className="container">
				<div className="title-ctn">
					<h4>회원가입</h4>
					<img src="../image/Line 13.png" alt="" />
					<h4>SIGN UP</h4>
				</div>
				<InputContainer className="input-ctn">
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
							확인
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
					<div className="input-row">
						<input
							className="email input"
							type="email"
							name=""
							id=""
							placeholder="이메일을 입력하세요."
						/>
						<Confirm type="button" className="confirm-btn">
							확인
						</Confirm>
					</div>
					<div className="input-row">
						<input
							className="address-input input"
							type="text"
							name=""
							id=""
							placeholder="주소를 입력하세요."
						/>
						<Confirm type="button" className="confirm-btn">
							검색
						</Confirm>
					</div>
				</InputContainer>
				<div className="btn-ctn">
					<Button type="button" className="btn register-confirm-btn">
						회원가입 완료
					</Button>
				</div>
			</div>
		</Wrapper>
	);
};

const Wrapper = styled.div`
	width: 613px;
	height: 689px;
`;

const Button = styled.button`
	background-color: #54b689;
`;

const InputContainer = styled.div`
	height: 452px;
`;

const Confirm = styled.button`
	width: 125px;
	height: 56px;
	background-color: #e7e4e4;
	margin: 0;
	padding: 0;
	margin-left: -5px;
`;

export default Register;
