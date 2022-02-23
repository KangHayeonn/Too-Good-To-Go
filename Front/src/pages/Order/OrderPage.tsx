import React, { useState, useCallback, useRef, useEffect } from "react";
import styled from "@emotion/styled";
import PageTemplate from "../../components/templates/PageTemplate";
import OrderForm from "../../components/organisms/Order/OrderForm";
import RequestForm from "../../components/organisms/Order/RequestForm";
import OrderList from "../../components/organisms/Order/OrderList";
import PaymentForm from "../../components/organisms/Order/PaymentForm";
import PayForm from "../../components/organisms/Order/PayForm";
import Modal from "../../components/atoms/Modal/OrderedModal";

const Button = styled.div`
	width: 100%;
	display: flex;
	justify-content: center;
	margin-top: 3em;
	margin-bottom: 7em;
`;

const OrderButton = styled.button`
	width: 21%;
	background: #387358;
	margin-top: 3em;
	padding: 0.7em;
	display: flex;
	flex-direction: column;
	align-items: center;
	font-size: 19px;
	font-weight: 600;
	box-shadow: 0px 1px 6px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
	color: #fff;

	&:hover {
		background-color: #a4a4a4;
	}
`;

const OrderPage: React.FC = () => {
	const [openModal, setOpenModal] = useState(false);
	const popRef = useRef<HTMLDivElement>(null);

	const onClickOutside = useCallback(
		({ target }) => {
			if (popRef.current && !popRef.current.contains(target)) {
				setOpenModal(false);
			}
		},
		[setOpenModal]
	);
	useEffect(() => {
		document.addEventListener("click", onClickOutside);
		return () => {
			document.removeEventListener("click", onClickOutside);
		};
	}, []);
	const onClickToggleModal = useCallback(() => {
		setOpenModal((prev) => !prev);
	}, [setOpenModal]);

	const showModal = useCallback(() => {
		return <Modal setModalOpen={setOpenModal} />;
	}, []);

	return (
		<PageTemplate isHeader={false} isSection={false} isFooter={false}>
			<OrderForm />
			<RequestForm />
			<OrderList />
			<PaymentForm />
			<PayForm />
			<Button ref={popRef}>
				<OrderButton type="button" onClick={onClickToggleModal}>
					주문하기
				</OrderButton>
			</Button>
			{!!openModal && showModal()}
		</PageTemplate>
	);
};

export default OrderPage;
