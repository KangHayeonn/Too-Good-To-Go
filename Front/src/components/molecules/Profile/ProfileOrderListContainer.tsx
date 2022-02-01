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
		"https://run.mocky.io/v3/acde94dc-1618-4d91-bae4-d57631a3b035",
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

const LoadingContainer = styled.div`
	min-height: 700px;
	max-height: auto;
	min-width: 671px;
`;

const OrderContainer = styled.div`
	margin: 0;
	padding: 0;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
`;

const DateDivider = styled.div`
	display: flex;
	width: 530px;
	height: 30px;
	/* border: 1px dotted red; */
	align-items: center;
	justify-content: space-between;

	p {
		font-size: 14px;
		font-weight: 700;
	}

	hr {
		width: 160px;
	}
`;

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

const OrderListContainer = styled.div`
	/* border: 1px dotted red; */
	/* width: 800px; */
	min-height: 700px;
	max-height: auto;
	min-width: 671px;

	/* margin-left: 36px; */
	margin-top: 80px;
	display: flex;
	flex-direction: column;
	align-items: center;
	padding-left: 18px;
`;

const ProfileCard = styled.div`
	width: 626px;
	height: 161px;
	border: 1px solid #d3d3d3;
	/* padding: 0px 10px 0px 0; */
	overflow: hidden;
	margin: 10px 25px 37px 0;
	display: flex;
	align-items: center;
	border-radius: 8px;
	justify-content: space-between;

	transition: 0.3s;

	.card-img-ctn {
		width: 134px;
	}

	img {
		width: 100%;
		overflow: hidden;
		margin-left: 14px;
		border-radius: 12px;
	}

	.cardInfo {
		display: flex;
		align-items: flex-start;
		flex-direction: column;
		padding-left: 20px;
		width: 300px;
		position: relative;
		right: 25px;

		.cardInfo-flex {
			display: flex;
			flex-direction: row;
			align-items: center;
			justify-content: flex-start;
			width: 100%;

			strong {
				margin-right: 10px;
			}
			.food-cost {
				position: relative;
				left: 90px;
				font-size: 13px;
				color: #736e6e;
				width: 65px;
			}
		}
	}

	.cardInfo > * {
		margin: 6px;
		margin-left: 25px;
	}

	p:last-child {
		font-weight: 600;
	}

	p > strong {
		color: #85bf70;
	}

	.card-info-text {
		font-size: 16px;
		.grey-text {
			margin: 5px;
			color: #c0baba;
		}
	}

	.button-wrapper {
		border-left: 1px solid #d6d6d6;
		height: 100%;
		width: 171px;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;

		button {
			border: 1px solid #f0f0f0;
			color: #646464;
			width: 161px;
			height: 25px;
			padding-top: 2px;
			margin: 8px;
			background-color: white;
			p {
				font-size: 16px;
				font-weight: 400;
			}
		}

		a {
			border: 1px solid #f0f0f0;
			color: #646464;
			width: 161px;
			height: 25px;
			padding-top: 2px;
			margin: 8px;
		}
	}
`;
function key(key: any) {
	throw new Error("Function not implemented.");
}
