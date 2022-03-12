import React, { useState } from "react";
import styled from "@emotion/styled";
import moment from "moment";
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
	width: calc(100% - 220px);
	height: 100%;
`;

const TimeText = styled.div`
	font-size: 30px;
	width: 160px;
	height: 100%;
	display: flex;
	font-weight: bold;
`;

const Confirm = styled.div`
	display: flex;
	align-items: center;
	justify-content: flex-end;
	width: 60px;
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
		transition: all 0.2s ease-in-out;
		&:hover {
			box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16),
				0 3px 6px rgba(0, 0, 0, 0.23);
		}
	}
`;

const PriceText = styled.p`
	font-size: 20px;
	font-weight: 600;
`;

type orderType = {
	orderId: number;
	orderTime: string;
	orderList: Array<string>;
	orderPrice: string;
	shopTell: string;
	orderDetail: string;
	status: string;
	payment: string;
	needDisposables: boolean;
	eta: string;
};

const ManagerOrderList: React.FC<orderType> = ({
	orderId,
	orderTime,
	orderList,
	orderPrice,
	shopTell,
	orderDetail,
	status,
	payment,
	needDisposables,
	eta,
}) => {
	const [isModal, setIsModal] = useState(false);
	const handleModal = () => {
		setIsModal(!isModal);
	};
	const time = moment(orderTime).format("YYYY년 MM월 DD일 hh시 mm분 ss초");
	return (
		<List>
			<TimeText>
				<p>{time.substring(14, 21)}</p>
			</TimeText>
			<Center>
				<p>{status}</p>
				<p>{orderList}</p>
				<PriceText>{orderPrice}원</PriceText>
			</Center>
			<Confirm>
				{status === "접수대기" ? (
					<button type="button" onClick={handleModal}>
						접수하기
					</button>
				) : (
					<button type="button" onClick={handleModal}>
						상세보기
					</button>
				)}
			</Confirm>
			{isModal && (
				<OrderModal
					modal={handleModal}
					shopTell={shopTell}
					orderDetail={orderDetail}
					payment={payment}
					createdTime={orderTime}
					orderId={orderId}
					status={status}
					needDisposables={needDisposables}
					etaLoad={eta}
				/>
			)}
		</List>
	);
};

export default ManagerOrderList;
