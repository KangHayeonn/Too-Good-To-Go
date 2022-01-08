import React, { useState } from "react";
import styled from "@emotion/styled";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";
import axios from "axios";
import { Route } from "react-router-dom";
import { useHistory } from "react-router";

const initialUserState = {
	email: "",
	password: "",
	name: "",
	phone: "",
	role: "user",
};

const Register: React.FC = () => {
	const [formValue, setFormValue] = useState(initialUserState);
	const history = useHistory();

	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		console.log(event.target);
		setFormValue({
			...formValue,
			[event.target.name]: event.target.value,
		});
		console.log(formValue);
	};

	const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();
		console.log("handlesubmit");
		const stringifiedFormValue = JSON.stringify(formValue);

		try {
			console.log("loginFormData: ", stringifiedFormValue);
			const response = await axios("http://54.180.134.20/api/signup", {
				method: "post",
				data: stringifiedFormValue,
				headers: { "Content-Type": "application/json" },
			});
			console.log("response: ", response);
			history.push({ pathname: "/" });
		} catch (error) {
			console.log("catch block: ", error);
		}
	};

	return (
		<Wrapper>
			<Container>
				<TitleCtn>
					<h4>회원가입</h4>
					<img src="image/Line 13.png" alt="" />
					<h4>SIGN UP</h4>
				</TitleCtn>
				<form onSubmit={(e) => handleSubmit(e)}>
					<FormControl component="fieldset">
						<RadioGroup
							row
							aria-label="role"
							name="controlled-radio-buttons-group"
							defaultValue="user"
							onChange={handleChange}
						>
							<FormControlLabel
								value="user"
								control={<Radio />}
								label="고객"
								name="role"
							/>
							<FormControlLabel
								value="seller"
								control={<Radio />}
								label="사장님"
								name="role"
							/>
						</RadioGroup>
					</FormControl>

					<InputCtn>
						<div className="input-row">
							<input
								className="email input"
								type="email"
								name="email"
								value={formValue.email}
								onChange={handleChange}
								id=""
								placeholder="이메일을 입력하세요."
							/>
							{/* <Confirm type="button" className="confirm-btn">
						//중복 확인 논리가 들어가야함
						</Confirm> */}
						</div>
						<div className="input-row">
							<input
								className="name input"
								type="text"
								name="name"
								onChange={handleChange}
								value={formValue.name}
								id=""
								placeholder="이름 입력하세요."
							/>
						</div>
						<div className="input-row">
							<input
								className="password input"
								type="password"
								name="password"
								id=""
								placeholder="비밀번호를 입력하세요."
							/>
						</div>
						<div className="input-row input-row-confirm-btn">
							<input
								className="password-confirm input"
								type="password"
								name="password"
								onChange={handleChange}
								value={formValue.password}
								id=""
								placeholder="비밀번호를 확인하세요."
							/>
							{/* <Confirm type="button" className="confirm-btn">
							확인
						</Confirm> */}
						</div>
						<div className="input-row">
							<input
								className="phone-input input"
								type="text"
								onChange={handleChange}
								value={formValue.phone}
								name="phone"
								id=""
								placeholder="핸드폰 번호를 입력하세요."
							/>
						</div>
					</InputCtn>
					<BtnCtn>
						<Confirm
							type="submit"
							className="btn register-confirm-btn"
						>
							회원가입 완료
						</Confirm>
					</BtnCtn>
				</form>
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
		width: 285px;
		height: 45px;
		margin: 10px;
		padding-left: 20px;
		border: 1px solid #c9cbca;
	}

	.input-row-confirm-button {
		width: 250px;
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
