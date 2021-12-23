import React from "react";
import styled from "@emotion/styled";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";

const Register: React.FC = () => {
	const [value, setValue] = React.useState("female");

	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		setValue((event.target as HTMLInputElement).value);
	};

	return (
		<Wrapper>
			<Container>
				<TitleCtn>
					<h4>회원가입</h4>
					<img src="image/Line 13.png" alt="" />
					<h4>SIGN UP</h4>
				</TitleCtn>
				<FormControl component="fieldset">
					<RadioGroup
						row
						aria-label="gender"
						name="controlled-radio-buttons-group"
						value={value}
						onChange={handleChange}
					>
						<FormControlLabel
							value="user"
							control={<Radio />}
							label="고객용"
						/>
						<FormControlLabel
							value="seller"
							control={<Radio />}
							label="사장님용"
						/>
					</RadioGroup>
				</FormControl>
				<InputCtn>
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
							placeholder="핸드폰 번호를 입력하세요."
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
	padding-bottom: 20px;
	border: 1px solid #c4c4c4;
	border-radius: 2px;
	padding: 10px;
	background-color: white;
	width: 480px;
	min-height: 620px;
	height: auto;
`;

const Container = styled.div`
	padding-bottom: 20px;

	display: flex;
	align-items: center;
	flex-direction: column;
`;

const TitleCtn = styled.div`
	font-weight: bold;
	color: #646464;
	margin-top: 40px;
	margin-bottom: 35px;
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
	margin-top: 30px;
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;

	input {
		font-size: 16px;
		font-weight: bold;
		width: 225px;
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
