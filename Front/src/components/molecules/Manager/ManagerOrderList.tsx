import React, { useState } from "react";
import styled from "@emotion/styled";
import OrderModal from "./OrderModal";

const List = styled.li`
	display: flex;
	width: calc(100% - 120px);
	justify-content: space-around;
	align-items: center;
	margin: 30px 60px 0 60px;
	padding-bottom: 30px;
	border-bottom: 1px solid #999;

	&:last-child {
		border-bottom: 0;
	}
`;
const Center = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	align-items: flex-start;
	width: calc(100% - 540px);
	height: 100%;
`;

const TimeText = styled.div`
	font-size: 30px;
	width: 140px;
	height: 100%;
	display: flex;
	font-weight: bold;
`;

const Confirm = styled.div`
	display: flex;
	align-items: center;
	justify-content: flex-end;
	width: 400px;
	& > button {
		cursor: pointer;
		background-color: #55b689;
		width: 60px;
		height: 70px;
		font-size: 13px;
		display: flex;
		align-items: center;
		justify-content: center;
		color: #fff;
	}
`;

const PriceText = styled.p`
	font-size: 20px;
	font-weight: 600;
`;

type orderType = {
	orderTime: string;
	orderList: Array<string>;
	orderPrice: string;
	shopTell: string;
	orderDetail: string;
	status: string;
	payment: string;
};

const ManagerOrderList: React.FC<orderType> = ({
	orderTime,
	orderList,
	orderPrice,
	shopTell,
	orderDetail,
	status,
	payment,
}) => {
	const [isModal, setIsModal] = useState(false);
	const handleModal = () => {
		setIsModal(!isModal);
		console.log(isModal);
	};
	return (
		<List>
			<TimeText>
				<p>{orderTime.substring(14, 20)}</p>
			</TimeText>
			<Center>
				<p>{status}</p>
				<p>{orderList}</p>
				<PriceText>{orderPrice}원</PriceText>
			</Center>
			<Confirm>
				<button type="button" onClick={handleModal}>
					접수하기
				</button>
			</Confirm>
			{isModal && (
				<OrderModal
					modal={handleModal}
					shopTell={shopTell}
					orderDetail={orderDetail}
					payment={payment}
					createdTime={orderTime}
				/>
			)}
		</List>
	);
};

export default ManagerOrderList;
