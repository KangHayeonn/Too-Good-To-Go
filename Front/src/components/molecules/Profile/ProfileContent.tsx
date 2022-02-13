import React, { useState } from "react";
import styled from "@emotion/styled/macro";
import { Link } from "react-router-dom";
import FormContainer from "./FormContainer";
import ProfileOrderList from "./ProfileOrderList";
import ProfileAddShop from "./ProfileAddShop";

type theme = {
	checked: boolean;
};

const ProfileContent: React.FC = () => {
	const [showProfileEdit, setShowProfileEdit] = useState<boolean>(true);
	const [showOrderList, setShowOrderList] = useState<boolean>(false);
	const [showManager, setShowManager] = useState<boolean>(false);
	const [dropmenuShow, setDropmenuShow] = useState<boolean>(false);

	function handleShowProfileEdit(
		event: React.MouseEvent<HTMLButtonElement, MouseEvent>
	) {
		event.preventDefault();
		setShowProfileEdit(true);
		setShowOrderList(false);
		setShowManager(false);
	}

	function handleShowOrderList(
		event: React.MouseEvent<HTMLButtonElement, MouseEvent>
	) {
		event.preventDefault();
		setShowOrderList(true);
		setShowProfileEdit(false);
		setShowManager(false);
	}

	function handleShowManager(
		event: React.MouseEvent<HTMLButtonElement, MouseEvent>
	) {
		event.preventDefault();
		setShowProfileEdit(false);
		setShowOrderList(false);
		setShowManager(true);
	}

	return (
		<Content>
			<SelectionContainer>
				<div className="button-ctn">
					<ProfileSelectButton
						type="button"
						onClick={(e) => handleShowProfileEdit(e)}
						checked={showProfileEdit}
					>
						개인정보 수정
					</ProfileSelectButton>
					<ProfileSelectButton
						type="button"
						onClick={(e) => handleShowOrderList(e)}
						checked={showOrderList}
					>
						주문 내역
					</ProfileSelectButton>
					<ProfileSelectButton
						type="button"
						onClick={(e) => {
							handleShowManager(e);
						}}
						checked={showManager}
					>
						매점 관리
					</ProfileSelectButton>
					{showManager && (
						<>
							<DropContent>
								<Link to="manager">
									<ProfileSelectButton
										type="button"
										checked={dropmenuShow}
									>
										교촌치킨 산본점
									</ProfileSelectButton>
								</Link>
								<Link to="shopedit" id="editBtn">
									수정
								</Link>
							</DropContent>
							<DropContent>
								<Link to="manager">
									<ProfileSelectButton
										type="button"
										checked={dropmenuShow}
									>
										강여사 김치찌개
									</ProfileSelectButton>
								</Link>
								<Link to="shopedit" id="editBtn">
									수정
								</Link>
							</DropContent>
						</>
					)}
				</div>
			</SelectionContainer>
			<ProfileEditContainer>
				{showProfileEdit && <FormContainer />}
				{showOrderList && <ProfileOrderList />}
				{showManager && <ProfileAddShop />}
			</ProfileEditContainer>
		</Content>
	);
};

export default ProfileContent;

const ProfileSelectButton = styled.button<theme>`
	/* border: 1px dashed green; */
	/* position: relative; */
	top: 200px;
	text-align: left;
	width: 100%;
	height: 50px;
	font-size: 20px;
	font-weight: 400;
	margin: 0px;
	padding-left: 20px;
	transition-duration: 0.2s;
	background-color: ${({ checked }) =>
		checked ? `rgba(49, 114, 84, 0.13)` : "white"};

	:hover {
		color: #6e6b6b;
	}
`;

const DropContent = styled.div`
	width: 100%;
	height: 40px;
	display: flex;
	align-items: center;

	#editBtn {
		margin-left: 40px;
		font-size: 15px;
		color: #000;
	}
	button {
		font-size: 18px;
		height: 100%;
	}
`;

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
	/* border: 1px dashed red; */
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
	}
`;
