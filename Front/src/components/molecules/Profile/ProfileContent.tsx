import React, { useState } from "react";
import styled from "@emotion/styled";
import FormContainer from "./FormContainer";
import ProfileOrderList from "./ProfileOrderList";

const ProfileContent: React.FC = () => {
	const [showProfileEdit, setShowProfileEdit] = useState<boolean>(true);
	const [showOrderList, setShowOrderList] = useState<boolean>(false);

	function handleShowProfileEdit(
		event: React.MouseEvent<HTMLButtonElement, MouseEvent>
	) {
		event.preventDefault();
		setShowProfileEdit(true);
		setShowOrderList(false);
	}

	function handleShowOrderList(
		event: React.MouseEvent<HTMLButtonElement, MouseEvent>
	) {
		event.preventDefault();
		setShowOrderList(true);
		setShowProfileEdit(false);
	}

	return (
		<Content>
			<SelectionContainer>
				<div className="button-ctn">
					<button
						type="button"
						onClick={(e) => handleShowProfileEdit(e)}
					>
						개인정보 수정
					</button>
					<button
						type="button"
						onClick={(e) => handleShowOrderList(e)}
					>
						Show order list
					</button>
				</div>
			</SelectionContainer>
			<ProfileEditContainer>
				{showProfileEdit && <FormContainer />}
				{showOrderList && <ProfileOrderList />}
			</ProfileEditContainer>
		</Content>
	);
};

export default ProfileContent;

const ProfileEditContainer = styled.div`
	margin: 0;
	padding: 0;
	border-left: 1px solid black;
	display: flex;
	flex-direction: column;
	/* width: 907px; */
	min-height: 650px;
	max-height: auto;
	/* background: #ffffff; */
	/* border: 1px solid red; */
`;

const Content = styled.div`
	margin: 0;
	padding: 0;
	border-left: 1px solid black;
	border-right: 1px solid black;
	border-bottom: 1px solid black;
	max-height: auto;
	display: flex;
	flex-direction: row;
	width: 907px;
	min-height: 660px;
	background: #ffffff;
	/* border: 1px solid #646464; */
`;

const SelectionContainer = styled.div`
	margin: 0;
	padding: 0;
	/* border: 1px solid black; */
	display: flex;
	flex-direction: column;
	width: 229px;
	min-height: 650px;
	max-height: auto;
	background: #ffffff;

	.button-ctn {
		position: relative;
		top: 40px;
		display: flex;
		flex-direction: column;
		align-items: center;

		button {
			/* position: relative; */
			top: 200px;
			text-align: left;
			width: 150px;
			height: 23px;
			font-size: 20px;
			font-weight: 400;
			margin: 5px;
			background-color: #ffffff;
			/* border: 1px solid red; */
			/* left: 70px; */
			transition-duration: 0.2s;
		}
		button:hover {
			color: #6e6b6b;
		}
	}
`;
