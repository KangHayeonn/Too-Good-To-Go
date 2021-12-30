import React, { useState } from "react";
import styled from "@emotion/styled";
// MUI
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Line13 from "../../../../public/image/Line 13.png";

type User = {
	name: string;
	email: string;
	password: number;
	phoneNumber: string;
};

const initialState = {
	name: "최지훈",
	email: "akdlsz21@gmail.com",
	password: 555132574,
	phoneNumber: "01055453287",
};

const FormContainer: React.FC = () => {
	const [state, setState] = useState<User>(initialState);

	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		setState({
			...state,
			// Dynamic state changing.
			[event.target.name]: event.target.value,
		});
	};

	const handleSubmit = (event: { preventDefault: () => void }) => {
		event.preventDefault();
		// eslint-disable-next-line no-alert
		alert(
			`이름: ${state.name}, 이메일: ${state.email}, 비번M: ${state.password}, 전번M: ${state.phoneNumber}`
		);
	};

	return (
		<FormWrapper>
			<EditTitle className="edit-title">
				<p>개인정보</p>
				<img src={Line13} alt="line13" />
				<p>MY PAGE</p>
			</EditTitle>
			<Wrapper>
				<form onSubmit={handleSubmit}>
					<div className="section-wrapper">
						<div className="profile-info">이름</div>
						<TextField
							id="outlined-helperText"
							name="name"
							disabled
							defaultValue={state.name}
							onChange={handleChange}
							margin="normal"
						/>
					</div>
					<div className="section-wrapper">
						<div className="profile-info">이메일(ID)</div>
						<TextField
							id="outlined-helperText"
							name="email"
							disabled
							defaultValue={state.email}
							onChange={handleChange}
							margin="normal"
						/>
					</div>

					<div className="section-wrapper">
						<div className="profile-info">비밀번호</div>
						<TextField
							name="password"
							defaultValue={state.password}
							onChange={handleChange}
							margin="normal"
						/>
					</div>

					<div className="section-wrapper">
						<div className="profile-info">핸드폰번호</div>
						<TextField
							id="outlined-helperText"
							name="phoneNumber"
							defaultValue={state.phoneNumber}
							onChange={handleChange}
							margin="normal"
						/>
					</div>

					<Button variant="outlined" type="submit">
						변경하기
					</Button>
				</form>
			</Wrapper>
		</FormWrapper>
	);
};

export default FormContainer;

const FormWrapper = styled.div`
	position: relative;
	left: 100px;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
`;

const EditTitle = styled.div`
	top: 26px;
	left: 37px;
	font-size: 20px;
	width: 350px;
	/* border: 1px solid purple; */
	display: flex;
	align-items: center;
	justify-content: space-between;
	margin-top: 45px;

	p {
		font-size: 20px;
		font-weight: 700;
		color: #646464;
	}
`;

const Wrapper = styled.div`
	margin: 0;
	padding: 0;
	/* width: 800px; */
	/* min-height: 290px; */
	height: auto;
	/* border: 1px dashed black; */
	margin-top: 10px;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	justify-content: space-between;
	padding-left: 38px;

	form {
		margin-top: 30px;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		min-height: 489px;
		.section-wrapper {
			/* border: 1px dashed green; */
			width: 400px;
			justify-content: space-between;
			align-items: center;
			font-size: 18px;
			display: flex;
			flex-direction: row;
		}
	}

	.input {
		width: 284px;
		height: 47px;
		border-radius: 6px;
	}

	button {
		/* position: relative; */
		/* top: 289px; */
		margin-top: 35px;
		width: 150px;
	}
`;
