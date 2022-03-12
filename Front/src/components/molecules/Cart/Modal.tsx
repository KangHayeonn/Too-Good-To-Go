import styled from "@emotion/styled/macro";
import React from "react";
import { useHistory } from "react-router-dom";

type PaymentContainerModalT = {
	setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
};

const Modal = ({ setIsModalOpen }: PaymentContainerModalT) => {
	const history = useHistory();

	// send user to order
	const handleClickSendUserToOrder = () => {
		history.push(`order`);
	};

	// close modal
	const handleClickCloseModal = () => {
		setIsModalOpen(false);
	};

	return (
		<>
			<Container>
				<Popup>
					<Header>
						<span className="head-title">선택 결제하기</span>
					</Header>
					<Body>
						<div className="body-contentbox">
							<MenuBox>주문하시겠습니까?</MenuBox>
						</div>
					</Body>
					<Footer>
						<button
							type="button"
							className="pop-btn confirm"
							id="confirm"
							onClick={() => handleClickSendUserToOrder()}
						>
							확인
						</button>
						<button
							type="button"
							className="pop-btn close"
							id="close"
							onClick={() => handleClickCloseModal()}
						>
							닫기
						</button>
					</Footer>
				</Popup>
			</Container>
		</>
	);
};

const Container = styled.div`
	background: rgba(0, 0, 0, 0.3);
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 15px;
	z-index: 2;
`;
const Popup = styled.div`
	width: 100%;
	max-width: 350px;
	border-radius: 10px;
	overflow: hidden;
	background: #6ec19b;
	box-shadow: 5px 10px 10px 1px rgba(0, 0, 0, 0.3);
	height: 189px;
`;
const Header = styled.div`
	width: 100%;
	height: 50px;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 19px;
	font-weight: bold;
	color: #404040;
`;
const Body = styled.div`
	width: 100%;
	height: 50px;
	background: #ffffff;
	display: flex;
	justify-content: center;
	padding-top: 30px;
`;
const Footer = styled.div`
	width: 100%;
	display: inline-flex;
	height: 40px;
	justify-content: center;
	align-items: center;
	float: left;
	color: #ffffff;
	cursor: pointer;

	.pop-btn {
		width: 50%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.pop-btn.confirm {
		border-right: 1px solid #d6e3dd; //오른쪽 줄
		width: 14em;
	}

	.pop-btn.close {
		width: 14em;
	}
`;
const MenuBox = styled.ul`
	width: 100%;
	height: 100%;
	min-height: 40px; //최소 높이
	max-height: 40px; //최대 높이
`;

export default Modal;
