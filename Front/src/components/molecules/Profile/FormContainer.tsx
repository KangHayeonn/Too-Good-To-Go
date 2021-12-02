import React, { useState } from "react";
import styled from "@emotion/styled";
// MUI
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";

type User = {
	id: string;
	email: string;
};

const initialState = {
	id: "Jihoon",
	email: "akdlsz21@gmail.com",
};

const FormContainer: React.FC = () => {
	const [state, setState] = useState<User>(initialState);

	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		setState({
			...state,
			[event.target.name]: event.target.value,
		});
	};

	const handleSubmit = (event: { preventDefault: () => void }) => {
		event.preventDefault();
		// eslint-disable-next-line no-alert
		alert(`변경된 패스워드: ${state.email}, 변경된 이름: ${state.id}`);
	};

	return (
		<>
			<EditTitle className="edit-title">개인정보 수정</EditTitle>
			<Wrapper>
				<form onSubmit={handleSubmit}>
					<TextField
						id="outlined-helperText"
						label="이름"
						name="id"
						defaultValue={state.id}
						helperText="변경할 이름을 입력하세요"
						onChange={handleChange}
						margin="normal"
					/>
					<TextField
						className="email-input input"
						id="outlined-helperText"
						type="email"
						name="email"
						defaultValue={state.email}
						value={state.email}
						onChange={handleChange}
						margin="normal"
					/>
					<Button variant="outlined" type="submit">
						변경하기
					</Button>
				</form>
			</Wrapper>
		</>
	);
};

export default FormContainer;

const EditTitle = styled.div`
	position: relative;
	width: 120px;
	top: 26px;
	left: 37px;
	font-size: 20px;
	/* border: 1px solid purple; */
`;

const Wrapper = styled.div`
	margin: 0;
	padding: 0;
	width: 800px;
	min-height: 600px;
	/* border: 1px dashed black; */
	margin-top: 30px;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	justify-content: center;
	padding-left: 38px;

	form {
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		justify-content: center;
	}

	.input {
		width: 284px;
		height: 47px;
		border-radius: 6px;
	}

	button {
		position: relative;
		top: 90px;
		width: 795px;
	}
`;
