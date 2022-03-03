import React, { useEffect, useState } from "react";
import styled from "@emotion/styled";
import TextField from "@mui/material/TextField";
import { useSelector } from "react-redux";
import Button from "@mui/material/Button";
import Line13 from "../../../../public/image/Line 13.png";
import patchUserInfoUpdateAxios from "../../../services/patchUserInfoUpdateAxios";
import { RootState } from "../../../app/store";
import {
	getUserLocalStorage,
	setUserLocalStorage,
} from "../../../helpers/userInfoControl";

export type UserT = {
	password: string;
	cpassword: string | undefined;
	phone: string | null;
};

const initialUser = {
	password: "",
	cpassword: "",
	phone: getUserLocalStorage().phone,
};

const FormContainer: React.FC = () => {
	const [formValue, setFormValue] = useState<UserT>(initialUser);
	const [validation, setValidation] = useState("");

	// input default value, bring in user's data from global
	const userInfo = useSelector((state: RootState) => {
		return state.user;
	});

	// logic for validating patch password
	useEffect(() => {
		if (formValue.password.length < 8) {
			setValidation("비밀번호는 최소 8자 이상이여야 합니다.");
		} else {
			setValidation("");
		}

		if (
			!(formValue.password === formValue.cpassword) &&
			formValue.password.length > 7
		) {
			setValidation("비밀번호가 일치하지 않습니다.");
		} else if (
			formValue.password === formValue.cpassword &&
			formValue.password.length > 7
		) {
			setValidation("");
		}
	}, [formValue]);

	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		setFormValue({
			...formValue,
			// Dynamic state changing.
			[event.target.name]: event.target.value,
		});

		console.log(formValue);
	};

	const updateUserPhoneNumberInLocalStorage = (
		phoneNumber: string | null
	) => {
		const newUserInfo = getUserLocalStorage();
		const newPhoneNumber = phoneNumber;
		newUserInfo.phone = newPhoneNumber;
		const stringifiedNewUserInfo = JSON.stringify(newUserInfo);
		setUserLocalStorage(stringifiedNewUserInfo);
	};

	const handleSubmit = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			// modifiy formValue to have no cpassword property
			delete formValue.cpassword;
			await patchUserInfoUpdateAxios.patchUserInfo(formValue);
			updateUserPhoneNumberInLocalStorage(formValue.phone);
		} catch (error) {
			console.error(error);
		}
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
							defaultValue={userInfo.name}
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
							defaultValue={userInfo.email}
							onChange={handleChange}
							margin="normal"
						/>
					</div>

					<div className="section-wrapper">
						<div className="profile-info">비밀번호</div>
						<TextField
							name="password"
							defaultValue={formValue.password}
							onChange={handleChange}
							margin="normal"
						/>
					</div>
					<div className="section-wrapper">
						<div className="profile-info">비밀번호 확인</div>
						<TextField
							name="cpassword"
							defaultValue={formValue.cpassword}
							onChange={handleChange}
							margin="normal"
						/>
					</div>
					<span>{validation}</span>

					<div className="section-wrapper">
						<div className="profile-info">핸드폰번호</div>
						<TextField
							id="outlined-helperText"
							name="phone"
							defaultValue={formValue.phone}
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
