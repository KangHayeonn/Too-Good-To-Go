import React, { useState } from "react";
import styled from "@emotion/styled";
import moment from "moment";
import ManagerOrderList from "../../molecules/Manager/ManagerOrderList";
import { orderData } from "../../molecules/OrderDummyData";

const Ul = styled.ul`
	width: 100%;\
	display: flex;
	flex-direction: column;
	align-items: center;
	overflow: auto;
`;

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

const checkStatus2 = (status: string) => {
	switch (status) {
		case "ORDER_COMPLETED":
			return "접수대기";
		case "PREPARING":
			return "처리중";
		case "WAITING_PICKUP":
			return "처리중";
		case "PICKUP_COMPLETED":
			return "완료";
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
				statusMatchName === checkStatus2(row.status) ||
				statusMatchName === "전체조회" ? (
					<ManagerOrderList
						key={row.id}
						orderTime={moment(row.createdAt).format(
							"YYYY년 MM월 DD일 hh시 mm분 ss초"
						)}
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
