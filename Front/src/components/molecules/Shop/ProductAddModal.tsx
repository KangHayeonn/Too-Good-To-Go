import React, { useState } from "react";
import styled from "@emotion/styled";
import CloseIcon from "@mui/icons-material/Close";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { getAccessToken } from "../../../helpers/tokenControl";
import { updateProductsBoolean } from "../../../features/editFeatures/updateProductBooelan";
import { RootState } from "../../../app/store";

const ModalMain = styled.div`
	display: flex;
	align-items: center;
	justify-content: center;
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: #00000080;
	z-index: 10000;
`;

const ModalWrap = styled.div`
	width: 375px;
	height: 410px;
	background-color: #fff;
	box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
`;

const ModalInner = styled.div`
	margin: 20px;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
`;

const BoldText = styled.p`
	font-size: 18px;
	font-weight: 600;
	margin-bottom: 10px;
	margin-top: 10px;
`;

const Text = styled.p`
	font-size: 15px;
	font-weight: 400;
	color: #999;
	padding-right: 10px;
`;

const Button = styled.div`
	width: 30%;
	height: 40px;
	background-color: #55b689;
	color: #fff;
	cursor: pointer;

	display: flex;
	justify-content: center;
	align-items: center;
`;

const Detail = styled.div`
	display: flex;
	align-items: center;
	margin-bottom: 5px;
	width: 100%;
`;

const ButtonWrap = styled.div`
	width: 100%;
	display: flex;
	justify-content: center;
	margin: 10px 0;
`;

const ModalHead = styled.div`
	width: 100%;
	height: 50px;
	background-color: #363636;
	color: #fff;
	font-weight: 500;
	display: flex;
	align-items: center;
	justify-content: space-between;
	& > p {
		margin-left: 20px;
	}

	& > svg {
		margin-right: 20px;
		cursor: pointer;
	}
`;

const InputStyle = styled.input`
	outline-style: none;
	border: 1px solid #999;
	width: auto;
`;

const InfoBox = styled.div`
	width: 100%;
	height: auto;
	display: flex;
	padding-bottom: 10px;
	flex-direction: column;
	align-items: flex-start;
`;

type modal = {
	modal: () => void;
	shopMatchId: string;
};

const ProductAddModal: React.FC<modal> = ({ modal, shopMatchId }) => {
	const [productInfo, setProductInfo] = useState({});
	const formData = new FormData();

	const productPost = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { name, value } = e.target;
		setProductInfo({
			...productInfo,
			[name]: value,
		});
	};

	const pricePost = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { name, valueAsNumber } = e.target;
		setProductInfo({
			...productInfo,
			[name]: valueAsNumber,
		});
	};
	let upfile: File;
	const onSaveFiles = (e: React.ChangeEvent<HTMLInputElement>) => {
		if (e.target.files) {
			const file: FileList = e.target.files;
			const uploadFile = file[0];
			upfile = uploadFile;
			console.log(upfile);
		}
	};
	const dispatch = useDispatch();
	const updateProductChange = useSelector((state: RootState) => {
		return state.updateProductBooelan;
	});

	const PRODUCT_API_URL = `http://54.180.134.20/api/manager/shops/${shopMatchId}/products`;
	const PostProductInfo = () => {
		const config = {
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
				"Content-Type": "multipart/form-data",
			},
		};
		return axios.post(PRODUCT_API_URL, formData, config);
	};
	const post = () => {
		const dtoObj = new Blob([JSON.stringify(productInfo)], {
			type: "application/json",
		});
		formData.set("request", dtoObj);
		console.log(upfile);
		if (upfile) {
			formData.append("file", upfile);
		} else {
			const imageJson = new File([], "");
			formData.set("file", imageJson);
		}
		// console.log(...formData);

		PostProductInfo().then(
			() => {
				console.log("post success");
				console.log(productInfo);
				if (updateProductChange) {
					dispatch(updateProductsBoolean(false));
				} else {
					dispatch(updateProductsBoolean(true));
				}
			},
			(err) => {
				console.log("post fail : ", err);
			}
		);
	};
	return (
		<ModalMain aria-hidden>
			<ModalWrap onClick={(e) => e.stopPropagation()} aria-hidden>
				<ModalHead>
					<p>상품 추가</p>
					<CloseIcon onClick={modal} />
				</ModalHead>
				<ModalInner>
					<InfoBox>
						<BoldText>상품 이름</BoldText>
						<Detail>
							<Text>상품 이름</Text>
							<InputStyle
								name="name"
								type="text"
								placeholder="상품 이름"
								onChange={productPost}
							/>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>상품 가격</BoldText>
						<Detail>
							<Text>상품 가격</Text>
							<InputStyle
								name="price"
								type="number"
								placeholder="상품 가격"
								onChange={pricePost}
							/>
						</Detail>
						<Detail>
							<Text>상품 할인 가격</Text>
							<InputStyle
								name="discountedPrice"
								type="number"
								placeholder="상품 할인 가격"
								onChange={pricePost}
							/>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>상품 이미지</BoldText>
						<Detail>
							<InputStyle
								name="image"
								type="file"
								accept="image/*"
								placeholder="상품 이미지"
								onChange={onSaveFiles}
							/>
						</Detail>
					</InfoBox>
					<ButtonWrap>
						<Button onClick={post}>추가</Button>
					</ButtonWrap>
				</ModalInner>
			</ModalWrap>
		</ModalMain>
	);
};

export default ProductAddModal;
