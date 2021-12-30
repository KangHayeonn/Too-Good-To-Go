import React, { useState } from "react";
import styled from "@emotion/styled";
import ManagerOrderList from "../../molecules/Manager/ManagerOrderList";
import { orderData } from "../../molecules/OrderDummyData";

const Ul = styled.ul`
	width: 100%;\
	display: flex;
	flex-direction: column;
	align-items: center;
	overflow: auto;
`;

const calcTime = (time: string) => {
	const year = time.substring(0, 4);
	const month = time.substring(5, 7);
	const day = time.substring(8, 10);
	const hour = time.substring(11, 12);
	const minute = time.substring(14, 16);
	const second = time.substring(17, 19);
	return `${year}년 ${month}월 ${day}일 ${hour}시 ${minute}분 ${second}초`;
};

const checkStatus = (status: string) => {
	switch (status) {
		case "ORDER_COMPLETED":
			return "접수완료";
		case "CANCELED":
			return "취소됨";
		case "PREPARING":
			return "준비중";
		case "WAITING_PICKUP":
			return "픽업 대기중";
		case "PICKUP_COMPLETED":
			return "완료";
		default:
			return "오류";
	}
};

const statusMatchFn = (status: string) => {
	switch (status) {
		case "접수대기":
			return "ORDER_COMPLETED";
		case "완료":
			return "CANCELED" || "PICKUP_COMPLETED";
		case "처리중":
			return "PREPARING" || "WAITING_PICKUP";
		default:
			return "오류";
	}
};

interface statusMatchType {
	statusMatchName: string;
}

const ManagerOrderLists: React.FC<statusMatchType> = ({ statusMatchName }) => {
	const [orderList] = useState(orderData);
	return (
		<Ul>
			{orderList.map((row) =>
				row.status.includes(`${statusMatchFn(statusMatchName)}`) ||
				statusMatchName === "전체조회" ? (
					<ManagerOrderList
						key={row.id}
						orderTime={calcTime(row.createdAt)}
						orderList={row.products.map(
							(r) => `${r.name} ${r.quantity.toString()}개 / `
						)}
						orderPrice={
							row.products
								.map((r) => r.price)
								.reduce((a, b) => a + b)
								.toString()
								.replace(/\B(?=(\d{3})+(?!\d))/g, ",") // 가격 1000단위 콤마
						}
						shopTell={row.users.phone}
						orderDetail={row.requirement}
						payment={row.payment}
						status={checkStatus(row.status)}
					/>
				) : null
			)}
		</Ul>
	);
};

export default ManagerOrderLists;
