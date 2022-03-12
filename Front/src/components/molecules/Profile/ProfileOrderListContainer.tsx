/* eslint-disable react/jsx-no-bind */
import React, { useState } from "react";
import styled from "@emotion/styled";

// import api from "./api/posts";
import useGetData from "../../atoms/useGetData";
import Modal from "./Modal";
import Line13 from "../../../../public/image/Line 13.png";
import OrderList from "./OrderList";

export type orderType = {
	id: number;
	shop: {
		id: number;
		name: string;
		categories: string[];
		phone: string;
		image: string;
	};
	products: {
		id: number;
		quantity: number;
		name: string;
		price: number;
		discountedPrice: number;
	}[];
	status: string;
	createdAt: Date;
	accept: boolean;
	pickupAt: string;
	request: string;
};

type ProductT = {
	id: number;
	quantity: number;
	name: string;
	price: number;
};

const ProfileOrderList: React.FC = () => {
	// Axios data cannot be added in order list.
	// product's image attribute missing.
	const initialState: Map<string, orderType[]> = new Map();
	const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
	const [cardNumber, setCardNumber] = useState<number>(0);

	// eslint-disable-next-line @typescript-eslint/no-unused-vars
	const [orderData, setOrderData] = useGetData(
		initialState,
		"http://54.180.134.20/api/orders",
		(er: unknown) => {
			console.log(er);
		}
	);

	const renderSwitch = (stat: string) => {
		switch (stat) {
			case "ORDER_COMPLETE":
				return "주문 완료";
			case "CANCELED":
				return "주문 취소";
			case "PREPARING":
				return "주문 준비중";
			case "WAITING_PICKUP":
				return "픽업 대기중";
			case "PICKUP_COMPLETED":
				return "픽업 완료";

			default:
				return "주문 대기중";
		}
	};

	function showModal(cardId: number) {
		// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
		const shop = Array.from(orderData.keys())
			// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
			.flatMap((key: string): Array<orderType> => orderData.get(key)!)
			.find((e: orderType) => {
				return e.shop.id === cardId;
			})!;
		console.log("shop:", shop);

		const orderedProduct = shop.products.map((e: ProductT) => {
			return e.name;
		});

		if (shop) {
			return (
				<Modal
					setIsModalOpen={setIsModalOpen}
					shopName={shop.shop.name}
					shopPhone={shop.shop.phone}
					request={shop.request}
					createdAt={shop.createdAt}
					orderedProduct={orderedProduct}
				/>
			);
		}
		return null;
	}
	const productLengthChecker = (productLength: number): string | null => {
		if (productLength > 1) {
			return ` 외 ${productLength - 1}개`;
		}
		return null;
	};

	return (
		<Wrapper>
			<EditTitle className="edit-title">
				<p>개인정보</p>
				<img src={Line13} alt="line13" />
				<p>ORDER LIST</p>
			</EditTitle>
			<OrderList
				showModal={showModal}
				renderSwitch={renderSwitch}
				orderData={orderData}
				productLengthChecker={productLengthChecker}
				setCardNumber={setCardNumber}
				setIsModalOpen={setIsModalOpen}
				isModalOpen={isModalOpen}
				cardNumber={cardNumber}
			/>
		</Wrapper>
	);
};

export default ProfileOrderList;

const Wrapper = styled.div`
	position: relative;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
`;

const EditTitle = styled.div`
	position: relative;
	right: 17px;
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
