import React, { useState } from "react";
import styled from "@emotion/styled";
// MUI
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";

type User = {
	id: string;
};

const initialState = {
	id: "akdlsz21@gmail.com",
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
		alert(`변경된 이름: ${state.id}`);
	};

	return (
		<>
			<EditTitle className="edit-title">개인정보 수정</EditTitle>
			<Wrapper>
				<form onSubmit={handleSubmit}>
					<div className="section-wrapper">
						<div className="profile-info">이름</div>
						<TextField
							id="outlined-helperText"
							label="아이디 ( 이메일 )"
							name="id"
							defaultValue={state.id}
							helperText="변경할 이메일을 입력하세요"
							onChange={handleChange}
							margin="normal"
						/>
					</div>
					<div className="section-wrapper">
						<div className="profile-info">이메일(ID)</div>
						<TextField
							id="outlined-helperText"
							label="아이디 ( 이메일 )"
							name="id"
							defaultValue={state.id}
							helperText="변경할 이메일을 입력하세요"
							onChange={handleChange}
							margin="normal"
						/>
					</div>

					<div className="section-wrapper">
						<div className="profile-info">비밀번호</div>
						<TextField
							id="outlined-helperText"
							label="아이디 ( 이메일 )"
							name="id"
							defaultValue={state.id}
							helperText="변경할 이메일을 입력하세요"
							onChange={handleChange}
							margin="normal"
						/>
					</div>

					<div className="section-wrapper">
						<div className="profile-info">핸드폰번호</div>
						<TextField
							id="outlined-helperText"
							label="아이디 ( 이메일 )"
							name="id"
							defaultValue={state.id}
							helperText="변경할 이메일을 입력하세요"
							onChange={handleChange}
							margin="normal"
						/>
					</div>

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
	/* width: 800px; */
	/* min-height: 290px; */
	height: auto;
	border: 1px dashed black;
	margin-top: 30px;
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
		width: 550px;
	}
`;
