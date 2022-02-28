import React from "react";
import { Link } from "react-router-dom";
import moment from "moment";
import CircularProgress from "@mui/material/CircularProgress";
import styled from "@emotion/styled";
import { EmotionJSX } from "@emotion/react/types/jsx-namespace";

type orderType = {
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

type OrderListPropsType = {
	orderData: Map<string, orderType[]>;
	showModal: (arg0: number) => EmotionJSX.Element | null;
	setCardNumber: React.Dispatch<React.SetStateAction<number>>;
	renderSwitch: (
		stat: string
	) =>
		| "주문 완료"
		| "주문 취소"
		| "주문 준비중"
		| "픽업 대기중"
		| "픽업 완료"
		| "주문 대기중";
	productLengthChecker: (productLength: number) => string | null;
	setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
	isModalOpen: boolean;
	cardNumber: number;
};

const OrderList: React.FC<OrderListPropsType> = ({
	orderData,
	renderSwitch,
	setCardNumber,
	showModal,
	productLengthChecker,
	setIsModalOpen,
	isModalOpen,
	cardNumber,
}: OrderListPropsType) => {
	const displayAggregatedPrice = (card: orderType) => {
		console.log("products", card.products);
		let priceTimesQuantity: number;

		return card.products.reduce((total, item) => {
			priceTimesQuantity = item.discountedPrice * item.quantity;
			return total + priceTimesQuantity;
		}, 0);
	};

	return orderData ? (
		<OrderListContainer>
			{Array.from(orderData.keys())
				.map((date): orderType[] => {
					// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
					return orderData.get(date)!;
				})
				.map((cards: orderType[]) => {
					return (
						<>
							<DateDivider>
								<hr />
								<p className="dateDivider">
									{`${cards[0].createdAt.getFullYear()} - ${
										cards[0].createdAt.getMonth() + 1
									} - ${cards[0].createdAt.getDate()}`}
								</p>
								<hr />
							</DateDivider>
							{cards.map((card: orderType) => {
								return (
									<OrderContainer key={card.id}>
										<ProfileCard>
											<div className="card-img-ctn">
												<img
													src={card.shop.image}
													alt="Food"
												/>
											</div>
											<div className="cardInfo">
												<div className="cardInfo-flex">
													<strong>
														{renderSwitch(
															card.status
														)}
													</strong>
													<p>
														{moment(card.pickupAt)
															.utc()
															.format(
																"HH시 mm분"
															)}
													</p>
													<p className="food-cost">
														{displayAggregatedPrice(
															card
														)}
														원
													</p>
												</div>
												<p className="card-info-text">
													<strong>
														{card.shop.categories}
													</strong>
													<span className="grey-text">
														|
													</span>
													{card.shop.name}
												</p>

												<p>
													{card.products[0].name}
													<span>
														<></>
													</span>
													{productLengthChecker(
														card.products.length
													)}
												</p>
											</div>
											<div className="button-wrapper">
												<button
													type="button"
													onClick={() => {
														setCardNumber(
															card.shop.id
														);
														setIsModalOpen(true);
													}}
												>
													<p>주문 정보</p>
												</button>
												<Link to="/"> 가게 정보 </Link>
											</div>
										</ProfileCard>
									</OrderContainer>
								);
							})}
						</>
					);
				})}
			{isModalOpen && showModal(cardNumber)}
		</OrderListContainer>
	) : (
		<LoadingContainer>
			<CircularProgress />
		</LoadingContainer>
	);
};

export default OrderList;

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
