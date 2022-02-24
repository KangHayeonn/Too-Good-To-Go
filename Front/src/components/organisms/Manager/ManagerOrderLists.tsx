import React, { useEffect, useState } from "react";
import styled from "@emotion/styled";
import axios from "axios";
import moment from "moment";
import ManagerOrderList from "../../molecules/Manager/ManagerOrderList";
import { orderData } from "../../molecules/OrderDummyData";
import { getAccessToken } from "../../../helpers/tokenControl";

const Ul = styled.ul`
	width: 100%;\
	display: flex;
	flex-direction: column;
	align-items: center;
	overflow: auto;
`;

const checkStatus = (status: string) => {
	switch (status) {
		case "WAITING_FOR_ACCEPTANCE":
			return "접수대기";
		case "CANCELED":
			return "취소됨";
		case "ACCEPTED":
			return "완료";
		default:
			return "오류";
	}
};

const checkStatus2 = (status: string) => {
	switch (status) {
		case "WAITING_FOR_ACCEPTANCE":
			return "접수대기";
		case "CANCELED":
			return "취소됨";
		case "ACCEPTED":
			return "완료";
		default:
			return "오류";
	}
};

interface statusMatchType {
	statusMatchName: string;
	shopMatchId: string;
}

type orderType = {
	id: number;
	user: {
		id: number;
		phone: string;
	};
	products: [
		{
			id: number;
			quantity: number;
			name: string;
			price: number;
			discountedPrice: number;
		}
	];
	status: string;
	requirement: string;
	paymentMethod: string;
	needDisposables: boolean;
	createdAt: string;
};

const ManagerOrderLists: React.FC<statusMatchType> = ({
	statusMatchName,
	shopMatchId,
}) => {
	const [orderList, setOrderList] = useState<orderType[]>([]);
	const MANAGER_ORDER_API_URL = `http://54.180.134.20/api/manager/shops/${shopMatchId}/orders`;
	const BoardService = () => {
		const config = {
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
			},
		};
		return axios.get(MANAGER_ORDER_API_URL, config);
	};
	useEffect(() => {
		BoardService().then(
			(res) => {
				setOrderList(res.data.data); // api가 연결 된 경우 -> back에서 데이터 불러옴
				console.log(res.data.data);
			},
			() => {
				console.log("error"); // api가 연결되지 않은 경우 -> 위의 예시 데이터 출력
			}
		);
	}, [statusMatchName]);
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
						shopTell={row.user.phone}
						orderDetail={row.requirement}
						payment={row.paymentMethod}
						status={checkStatus(row.status)}
					/>
				) : null
			)}
		</Ul>
	);
};

export default ManagerOrderLists;
