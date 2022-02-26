import React, { useState } from "react";
import { css } from "@emotion/react";
import ProductAddModal from "./ProductAddModal";

const header = css`
	display: flex;
	align-items: center;
	margin-left: 5px;
	justify-content: space-between;
	padding-top: 50px;
	padding-bottom: 40px;
`;

const headerText = css`
	position: relative;
	font-size: 36px;
	font-weight: 800;
	color: #333;
`;

const addProductBtn = css`
	width: 89px;
	height: 27px;
	border-radius: 8px;
	background-color: #55b689;
	color: #fff;
	font-weight: 500;
	font-size: 13px;
`;

type shopMenuType = {
	children: React.ReactNode;
	isEdit: boolean;
	shopMatchId: string;
};

const ShopMenuHeader: React.FC<shopMenuType> = ({
	children,
	isEdit,
	shopMatchId,
}) => {
	const [isModal, setIsModal] = useState(false);
	const handleModal = () => {
		setIsModal(!isModal);
	};
	return (
		<div css={header}>
			<h1 css={headerText}>{children}</h1>
			{isEdit ? (
				<>
					<button
						type="button"
						css={addProductBtn}
						onClick={handleModal}
					>
						상품 추가 +
					</button>
					{!!isModal && (
						<ProductAddModal
							modal={handleModal}
							shopMatchId={shopMatchId}
						/>
					)}
				</>
			) : null}
		</div>
	);
};

export default ShopMenuHeader;
