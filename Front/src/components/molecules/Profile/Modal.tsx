import React, { useRef } from "react";
import styled from "@emotion/styled";
import moment from "moment";
import useOnClickOutside from "./hooks/use-onclick-outside";

type ModalProps = {
	setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
	shopName: string;
	shopPhone: number;
	request: string;
	createdAt: string;
	orderedProduct: string[];
};

const Modal: React.FC<ModalProps> = ({
	setIsModalOpen,
	shopName,
	shopPhone,
	request,
	createdAt,
	orderedProduct,
}: ModalProps) => {
	const orderCreatedTime = moment(createdAt)
		.utc()
		.format("YYYY-MM-DD HH시 MM분");

	const orderedMenu = orderedProduct.join(", ");

	// Logic to close modal when clicked outside of element
	const squareBoxRef = useRef<HTMLDivElement>(null);

	const clickOutsidehandler = () => {
		setIsModalOpen(false);
	};
	useOnClickOutside(squareBoxRef, clickOutsidehandler);

	return (
		<Wrapper ref={squareBoxRef}>
			<h3 className="modal-title">주문 상세정보</h3>
			<hr />
			<div className="modal-content-container">
				<p>가게 이름 : {shopName}</p>
				<p>가게 전화번호 : {shopPhone}</p>
				<p>요청사항 : {request}</p>
				<p>Time of Order : {orderCreatedTime}</p>
				<p>주문한 메뉴 : {orderedMenu}</p>
			</div>
			<CloseModalButton onClick={() => setIsModalOpen(false)}>
				닫기
			</CloseModalButton>
		</Wrapper>
	);
};

export default Modal;

const Wrapper = styled.div`
	display: flex;
	flex-direction: column;
	width: 370px;
	min-height: 410px;
	height: auto;
	background-color: white;
	border: 1px solid black;
	position: absolute;
	align-items: center;
	box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
	justify-content: space-between;

	.modal-title {
		margin-top: 18px;
		margin-bottom: -20px;
	}

	hr {
		width: 84%;
		margin-bottom: 12px;
	}
	.modal-content-container {
		/* border: 1px dotted purple; */
		width: 90%;
		height: 190px;
		padding-left: 35px;
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		justify-content: flex-start;
	}
	p {
		/* border: 1px solid red; */
		margin-top: 15px;
		text-align: left;
	}
`;

const CloseModalButton = styled.button`
	width: 171px;
	height: 41px;
	background-color: #f7f8f5;
	border: 1px solid #d8d8d8;
	margin-bottom: 18px;
`;
