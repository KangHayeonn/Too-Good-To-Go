import React from "react";
import styled from "@emotion/styled";

type ModalProps = {
	setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
	shopName: string | null;
	shopFoodCost: number | null;
};

function Modal({ setIsModalOpen, shopName, shopFoodCost }: ModalProps) {
	return (
		<Wrapper>
			<p>가게 전화번호 : {shopName}</p>
			<p>요청사항 : {shopFoodCost}</p>

			<CloseModalButton onClick={() => setIsModalOpen(false)}>
				닫기
			</CloseModalButton>
		</Wrapper>
	);
}

export default Modal;

const Wrapper = styled.div`
	display: flex;
	flex-direction: column;
	width: 370px;
	height: 360px;
	background-color: #fef5e9;
	position: absolute;
	align-items: center;
`;

const CloseModalButton = styled.button`
	width: 171px;
	height: 41px;
	background-color: #f7f8f5;
	border: 1px solid #d8d8d8;
`;
