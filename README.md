# Credit Union of Colorado Mortgage Interest Rate Checker

![ci](https://github.com/StevenMassaro/cuofco-mortgage-rate-check/actions/workflows/build.yml/badge.svg)
[![Docker](https://badgen.net/badge/icon/docker?icon=docker&label)](https://hub.docker.com/r/stevenmassaro/cuofco-mortgage-rate-check)

Automatically check mortgage interest rates at the [Credit Union of Colorado (CUofCO)](https://www.cuofco.org/), and notify through Telegram.

### Motivation

I want to stay updated about interest rates, so I know whether to refinance my mortgage or not.

## Usage

### Environment variables

| Name               | Required | Default value | Example value                                                 |
|--------------------|----------|---------------|---------------------------------------------------------------|
| `PURCHASE_PRICE`   | Yes      | `450000`      | `450000`                                                      |
| `DOWN_PAYMENT`     | Yes      | `50000`       | `50000`                                                       |
| `ZIP_CODE`         | Yes      | `80001`       | `80001`                                                       |
| `BOT_TOKEN`        | Yes      |               | `bot123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11`                |
| `CHAT_ID`          | Yes      |               | `123456789`                                                   |
| `HEALTHCHECKS_URL` | No       |               | `https://hc-ping.com/00000000-0000-0000-0000-000000000000`    |
| `DELAY`            | No       | `1d`          | `7d` (or any other permissible value for the `sleep` command) |

`BOT_TOKEN` and `CHAT_ID` are obtained after [creating your own Telegram bot](https://core.telegram.org/bots/tutorial).

### Docker

```dockerfile
  cuofco-mortgage-rate-check:
    image: stevenmassaro/cuofco-mortgage-rate-check
    container_name: cuofco
    cap_add:
      # SYS_ADMIN permissions are necessary for Selenium to operate correctly.
      - SYS_ADMIN
    environment:
      PURCHASE_PRICE: 450000
      DOWN_PAYMENT: 50000
      ZIP_CODE: 12345
      BOT_TOKEN: bot123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11
      CHAT_ID: 123456789
      HEALTHCHECKS_URL: https://hc-ping.com/00000000-0000-0000-0000-000000000000
```